package kr.huni.signup.service;

import kr.huni.signup.domain.Coupon;
import kr.huni.signup.domain.User;
import kr.huni.signup.domain.UserCoupon;
import kr.huni.signup.repository.CouponRepository;
import kr.huni.signup.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    
    private static final String WELCOME_COUPON_CODE = "WELCOME10";
    
    @Transactional
    public UserCoupon issueWelcomeCoupon(User user) {
        // Find the welcome coupon
        Coupon welcomeCoupon = couponRepository.findByCode(WELCOME_COUPON_CODE);
        
        if (welcomeCoupon == null) {
            throw new RuntimeException("Welcome coupon not found");
        }
        
        // Check if the user already has this coupon
        if (userCouponRepository.existsByUserAndCoupon(user, welcomeCoupon)) {
            throw new RuntimeException("User already has the welcome coupon");
        }
        
        // Create a new user coupon
        UserCoupon userCoupon = UserCoupon.builder()
                .user(user)
                .coupon(welcomeCoupon)
                .issuedAt(LocalDateTime.now())
                .isUsed(false)
                .build();
        
        // Save and return the user coupon
        return userCouponRepository.save(userCoupon);
    }
}
