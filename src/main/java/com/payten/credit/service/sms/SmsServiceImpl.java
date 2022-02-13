package com.payten.credit.service.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService{
    @Override
    public void sendSmsToPhoneNumber(String phoneNo) {
        log.info("Sms sent to: " +phoneNo);
    }
}
