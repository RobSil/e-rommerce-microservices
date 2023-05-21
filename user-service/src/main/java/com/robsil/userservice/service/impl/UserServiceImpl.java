package com.robsil.userservice.service.impl;


import com.robsil.erommerce.userentityservice.data.domain.ERole;
import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.userservice.data.repository.UserRepository;
import com.robsil.model.exception.grpc.GrpcNotFoundException;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.model.exception.http.HttpConflictException;
import com.robsil.model.exception.http.UnauthorizedException;
import com.robsil.userservice.user.UserRegistrationRequest;
import com.robsil.proto.Id;
import com.robsil.proto.Str;
import com.robsil.proto.UserServiceGrpc;
import com.robsil.userservice.service.UserService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
//@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("getByEmail: user not found. UserID: %s".formatted(userId));
                    return new EntityNotFoundException("User not found");
                });
    }

    @Override
    public void findById(Id request, StreamObserver<com.robsil.proto.User> responseObserver) {
        try {
            responseObserver.onNext(findById(request.getId()).toProto());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(new GrpcNotFoundException(e));
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void findByEmail(Str request, StreamObserver<com.robsil.proto.User> responseObserver) {
        try {
            responseObserver.onNext(findByEmail(request.getText()).toProto());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(new GrpcNotFoundException(e));
        }
    }

    @Override
    public User findByPrincipal(Principal principal) {
        if (principal == null) throw new UnauthorizedException("UNAUTHORIZED");
        return findByEmail(principal.getName());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.info("getByEmail: user not found. Email: %s".formatted(email));
                    return new EntityNotFoundException("User not found");
                });
    }

    @Override
    @Transactional
    public User saveEntity(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void register(UserRegistrationRequest dto) {

        if (existsByEmail(dto.getEmail())) {
            throw new HttpConflictException("Email is already occupied.");
        }

        var user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isEnabled(true)
                .build();

        user.getRoles().add(ERole.USER);

        saveEntity(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public long countAll() {
        return userRepository.count();
    }
}
