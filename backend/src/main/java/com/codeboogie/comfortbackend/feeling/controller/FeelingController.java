package com.codeboogie.comfortbackend.feeling.controller;

import com.codeboogie.comfortbackend.feeling.model.FeelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import com.codeboogie.comfortbackend.feeling.model.Feeling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 한승남
 * @version 1.0, 2021.09.28 소스 수정
 * 감정 기록 API RestController
 *
*/


@RestController
@RequestMapping("api")
public class FeelingController {

    @Autowired
    private MongoTemplate mongoTemplate; //몽고DB 템플릿 불러오기

    @Autowired
    private FeelingService feelingService;

    @RequestMapping(path="/insert", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void insert(@RequestBody final Feeling feeling) {
        try {
            feelingService.insert(feeling);
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path="/update", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void update(@RequestBody final Feeling feeling) {
        try {
            feelingService.update(feeling);
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path="/remove", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void remove(@RequestBody final Feeling feeling) {
        String key = "id";
        String value = feeling.getId();
        try {
            feelingService.remove(key, value);
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    //하루 글 썻는지 조회
    @RequestMapping(value="/history", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody long history(@RequestBody String userId, String date) {
        System.out.println("안드로이드 -> 서버로 Post 요청 userId:"+ userId + " date:" + date);

        return feelingService.findDatas(userId, date);
    }

    // 전체 글 조회
    @RequestMapping(path="/loadHistory", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Feeling> loadHistory(@RequestBody Long userId) {
        System.out.println("안드로이드 -> 서버로 Post 요청 userId:"+ userId);

        return feelingService.loadHistory(userId);
    }

    /*//댓글 조회
    @RequestMapping(path="/load_cmt", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<HashMap> load_cmt(@RequestBody Long userId) {
        System.out.println("안드로이드 -> 서버로 Post 요청 userId:"+ userId);

        return a;
    }*/

    // 그래프 조회 년월일 전송 받을시 스코어 리턴
    // String 데이터 : userId, startDate & endDate (DateFormat : yyyy-MM-dd'T'HH:mm:ss.SSS'Z')
    @RequestMapping(path="/graph", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Feeling> graph(@RequestBody HashMap<String, String> data) throws Exception {
        System.out.println("안드로이드 -> 서버로 Post 요청 :"+ data);

        return feelingService.getGraph(data.get("userId"), data.get("startDate"), data.get("endDate"));
    }

    // 그래프 월별 조회 : String 데이터 : userId / 년-월 (ex. 2021-10)
    @RequestMapping(path="/graphMonth", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Feeling> graphMonth(@RequestBody HashMap<String, String> data) throws Exception {
        System.out.println("안드로이드 -> 서버로 Post 요청 :"+ data);

        return feelingService.getGraphMonth(data.get("userId"), data.get("month"));
    }



}
