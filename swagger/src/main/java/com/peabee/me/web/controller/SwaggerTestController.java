package com.peabee.me.web.controller;

import com.peabee.me.common.Result;
import com.peabee.me.common.ResultUtil;
import com.peabee.me.domain.User;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pengbo on 16-4-28.
 */
@Controller
@RequestMapping("/user")
@Api(value = "user-api", description = "有关于用户的CURD操作", position = 5)
public class SwaggerTestController {

    /**
     * 查询
     * @param pk
     * @return
     */
    @RequestMapping(value = "/{pk}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据pk查找用户", notes = "返回用户实体对象", response = User.class, position = 3)
    public ResponseEntity<?> getUser(
            @ApiParam(value = "填写Pk", allowableValues = "range[1,5]", required = true, defaultValue = "0", allowMultiple = true) @PathVariable("pk") Integer pk){
        User user = new User();
        user.setId(pk);
        user.setName("pengbo" + pk);
        Result<User> result = ResultUtil.buildSuccessResult(user);
        return new ResponseEntity<Result<User>>(result, HttpStatus.OK);
    }

    /**
     * 查询
     * @param pk
     * @return
     */
    @RequestMapping(value = "/{pk}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "根据pk删除用户", notes = "返回是否成功", response = Integer.class, position = 3)
    public ResponseEntity<?> deleteUser(
            @ApiParam(value = "填写Pk", allowableValues = "range[1,5]", required = true, defaultValue = "0", allowMultiple = true) @PathVariable("pk") Integer pk){
        Result<Integer> result = new Result<Integer>();
        result.setData(pk);
        result.setStatus(true);
        return new ResponseEntity<Result<Integer>>(result, HttpStatus.OK);
    }
}
