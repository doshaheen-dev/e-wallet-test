package com.tml.poc.Wallet.services;

import com.tml.poc.Wallet.exception.InvalidInputException;
import com.tml.poc.Wallet.exception.ResourceNotFoundException;
import com.tml.poc.Wallet.models.OTPModel;
import com.tml.poc.Wallet.models.UserModel;
import com.tml.poc.Wallet.models.UserRegistrationModel;
import com.tml.poc.Wallet.models.mpin.MPINModel;
import com.tml.poc.Wallet.repository.UserOTPRepository;
import com.tml.poc.Wallet.utils.CommonMethods;
import com.tml.poc.Wallet.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OTPService {

    @Autowired
    private UserOTPRepository userOTPRepository;

    @Value("${otp.expiretime.miliseco}")
    private long otpExpireTime;

    /**
     * Create OTP to User
     * @param userid
     * @return
     */
    public OTPModel getUserOTPCreate(long userid){
        String OTP=new CommonMethods().generateOTP();
        OTPModel otpModel=new OTPModel();
        otpModel.setOtp(OTP);
        otpModel.setUserID(userid);
        otpModel = userOTPRepository.save(otpModel);
        return otpModel;
    }


    /**
     * Create OTP to MPIN
     * @param mpinID
     * @return
     */
    public OTPModel getMPINOTPCreate(long mpinID){
        OTPModel otpModel=new OTPModel();
        otpModel.setOtp(new CommonMethods().generateOTP());
        otpModel.setMPinId(mpinID);

        otpModel = userOTPRepository.save(otpModel);

        return otpModel;
    }

    /**
     * MPIN OTP Verification
     * @param mpinModel
     * @return
     * @throws ResourceNotFoundException
     */
    public Object verifyOTP(MPINModel mpinModel,String otp) throws ResourceNotFoundException {

        Optional<OTPModel> otpModelOptional=
                userOTPRepository.findFirstByMPinId(mpinModel.getId());
        if(!otpModelOptional.isPresent()){
            throw new ResourceNotFoundException("OTP Not Found");
        }
        if(otp.equals(otpModelOptional.get().getOtp())){
            return otpModelOptional.get();
        }else {
            throw new ResourceNotFoundException("OTP Not matched");

        }
    }


    /**
     * get OTP By userid and check otp
     * @param userid
     * @param otp
     * @return
     * @throws ResourceNotFoundException
     * @throws InvalidInputException
     */
    public boolean verifyOTP(long userid,String otp) throws ResourceNotFoundException, InvalidInputException {

        Optional<OTPModel> otpModelOptional=
                userOTPRepository.findAllByUserID(userid);
        if(!otpModelOptional.isPresent()){
            throw new ResourceNotFoundException("OTP Not Found");
        }
        if(verifyOTPByDate(otpModelOptional.get(),otp)){
            return true;
        }else {
            throw new ResourceNotFoundException("OTP Not matched");
        }
    }


    /**
     * verifyDate And OTP
     * @param otpModel
     * @param otp
     * @return
     * @throws InvalidInputException
     */
    private boolean verifyOTPByDate(OTPModel otpModel, String  otp)
            throws InvalidInputException {
        if (otpModel != null && otpModel.getOtp().equals(otp)) {
            Date expireDate = new Date((otpModel.getCreatedAt().getTime() + otpExpireTime));
            Date currentDate = new Date(System.currentTimeMillis());
            if (currentDate.before(expireDate)) {
                return true;
            } else {
                throw new InvalidInputException("OTP Expired");
            }
        } else {
            throw new InvalidInputException("Invalid OTP");
        }
    }


}
