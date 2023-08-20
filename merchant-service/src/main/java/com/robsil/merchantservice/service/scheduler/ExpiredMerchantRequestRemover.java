package com.robsil.merchantservice.service.scheduler;

import com.robsil.merchantservice.service.MerchantRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpiredMerchantRequestRemover {

    private final MerchantRequestService merchantRequestService;
    private final Clock clock;

    @Scheduled(cron = "0 * * * * *")
    public void removeExpiredRequests() {
        merchantRequestService.deleteAllPendingObsolete(LocalDateTime.now(clock));
    }

}
