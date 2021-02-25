package com.tml.poc.Wallet.restController;

import com.tml.poc.Wallet.dao.user.SearchCriteria;
import com.tml.poc.Wallet.iDao.IUserDAO;
import com.tml.poc.Wallet.models.usermodels.UserModel;
import com.tml.poc.Wallet.repository.UserRepository;
import com.tml.poc.Wallet.services.UserSearchService;
import com.tml.poc.Wallet.utils.ApplicationConstants;
import com.tml.poc.Wallet.utils.CommonMethods;
import com.tml.poc.Wallet.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("user/search")
public class UserSearchController {

    @Autowired
    private IUserDAO api;

    @Autowired
    private UserSearchService userSearchService;
    @GetMapping("/mobile/{mobileNumber}")
    private ResponseEntity getUserByMobileNumber(@PathVariable(name = "mobileNumber")String mobileNumber){
        return userSearchService.searchUserByMobile(mobileNumber);
    }

    @GetMapping("/qrcode/{qrCode}")
    private ResponseEntity getUserByQRCode(@PathVariable(name = "qrCode")String qrCode){
        return userSearchService.searchUserByUUID(qrCode);
    }

    @GetMapping("/getAll")
    private ResponseEntity getAllUser(){
        return userSearchService.getAllUser();
    }



    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<UserModel> findAll(@RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "fromDate", required = false)
                                   @DateTimeFormat(pattern= Constants.TIME_DATE) Date fromDdate,
                                   @RequestParam(value = "toDate", required = false)
                                       @DateTimeFormat(pattern= Constants.TIME_DATE) Date toDate,
                                   @RequestParam(value = "pageSize",defaultValue ="20", required = false) int pageSize,
                                   @RequestParam(value = "pageNo",defaultValue ="0",required = false) int pageNo) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1),
                        matcher.group(2), matcher.group(3)));

            }
        }

        return api.searchUser(params,fromDdate,toDate,pageSize,pageNo);
    }
}
