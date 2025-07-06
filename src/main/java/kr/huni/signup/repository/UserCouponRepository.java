package kr.huni.signup.repository;

import kr.huni.signup.domain.Coupon;
import kr.huni.signup.domain.User;
import kr.huni.signup.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUser(User user);
    List<UserCoupon> findByUserAndCoupon(User user, Coupon coupon);
    boolean existsByUserAndCoupon(User user, Coupon coupon);
}
