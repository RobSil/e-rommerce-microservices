package com.robsil.erommerce.jwtintegration.filter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.jwtintegration.util.StringUtil;
import com.robsil.model.exception.http.ServiceUnavailableException;
import com.robsil.proto.AuthenticationServiceGrpc;
import com.robsil.proto.Token;
import com.robsil.proto.VerificationResponse;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtClientServiceVerifierFilter extends OncePerRequestFilter {

    private final EurekaClient eurekaClient;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    //    @GrpcClient("authorization-service")
    private AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtil.isNullOrEmpty(bearerToken) || !bearerToken.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authenticationServiceBlockingStub == null) {
            authenticationServiceBlockingStub = initAuthenticationServiceBlockingStub();
        }

        String authToken = bearerToken.replace(BEARER_PREFIX, "");

        VerificationResponse authenticationResponse = null;

        try {
            authenticationResponse = authenticationServiceBlockingStub.verifyToken(Token.newBuilder()
                    .setValue(authToken)
                    .setCheckIsMerchant(true)
                    .build());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().equals(Status.UNAUTHENTICATED)) {
                log.debug("Got unauthenticated from authentication-service.");
                filterChain.doFilter(request, response);
                return;
            }

            authenticationServiceBlockingStub = null;

            throw e;
        }

        Authentication authentication = new UserAuthenticationToken(authenticationResponse.getUsername(),
                null,
                authenticationResponse.getAuthoritiesList().stream().map(SimpleGrantedAuthority::new).toList(),
                authenticationResponse.getId(),
                authenticationResponse.getIsMerchant());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    AuthenticationServiceGrpc.AuthenticationServiceBlockingStub initAuthenticationServiceBlockingStub() {
        InstanceInfo instanceInfo = null;
        try {
            instanceInfo = eurekaClient.getNextServerFromEureka("authorization-service", false);
        } catch (RuntimeException e) {
            if (e.getMessage().startsWith("No matches for the virtual host name")) {
                throw new ServiceUnavailableException("Didn't found any authorization service. Maybe not fetched yet.");
            }

            throw e;
        }
        var channel = ManagedChannelBuilder
                .forTarget("dns:///" + instanceInfo.getIPAddr() + ":" + instanceInfo.getMetadata().get("gRPC_port"))
                .usePlaintext()
                .build();
        return AuthenticationServiceGrpc.newBlockingStub(channel);
    }
}
