package com.flicker.bff.application;

import com.flicker.bff.common.module.response.ResponseDto;
import com.flicker.bff.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BffMovieService {

    private final Util util; // Util 클래스 의존성 주입

    @Value("${movie.baseurl}")
    private String movieBaseUrl; // 영화 서버 API의 기본 URL

    @Value("${user-review.baseurl}")
    private String userBaseUrl; // 사용자-리뷰 서버 API의 기본 URL

    @Value("${batch.baseurl}")
    private String batchBaseUrl; // 배치서버 API의 기본 URL

    @Value("${recommend.baseurl}")
    private String recommendBaseUrl; // 추천서버 API의 기본 URL


    public ResponseEntity<ResponseDto> createMovie(MovieCreateRequest request) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/create");
        // 2. POST 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendPostRequest(movieBaseUrl, path, request);
    }

    public ResponseEntity<ResponseDto> updateMovie(MovieUpdateRequest request) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/update/detail");
        // 2. PUT 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendPutRequest(movieBaseUrl, path, request);
    }

    public ResponseEntity<ResponseDto> deleteMovie(int movieSeq) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/delete?movieSeq=" + movieSeq);
        // 2. PUT 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendPutRequest(movieBaseUrl, path, null);
    }

    public ResponseEntity<ResponseDto> addActor(ActorAddRequest request) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/add/actor");
        // 2. POST 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendPostRequest(movieBaseUrl, path, request);
    }

    public ResponseEntity<ResponseDto> deleteActor(int actorSeq, int movieSeq) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/delete/actor/" + actorSeq + "/" + movieSeq);
        // 2. DELETE 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendDeleteRequest(movieBaseUrl, path);
    }

    public ResponseEntity<ResponseDto> updateActor(ActorAddRequest request) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/update/actor");
        // 2. PUT 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendPutRequest(movieBaseUrl, path, request);
    }

    public ResponseEntity<ResponseDto> getMovieList(int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/" + page + "/" + size);
        // 2. GET 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendGetRequest(movieBaseUrl, path);
    }

    public ResponseEntity<ResponseDto> getMovieListByGenre(String genre, int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/genre/" + genre + "/" + page + "/" + size);
        // 2. GET 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendGetRequest(movieBaseUrl, path);
    }

    public ResponseEntity<ResponseDto> getMovieListByActor(String actorName, int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/actor/" + actorName + "/" + page + "/" + size);
        // 2. GET 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendGetRequest(movieBaseUrl, path);
    }

    public ResponseEntity<ResponseDto> getMovieListBySearch(String keyword, int userSeq, int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/search/" + keyword + "/" + userSeq + "/" + page + "/" + size);
        // 2. GET 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendGetRequest(movieBaseUrl, path);
    }

    // TODO: 영화 상세조회 + 연관 영화 추천  + 좋아요 높은 리뷰 조회
    public ResponseEntity<ResponseDto> getMovieDetail(int movieSeq, int userSeq) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/detail/" + movieSeq + "/" + userSeq);
        // 2. GET 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendGetRequest(movieBaseUrl, path);
    }
    
    // TODO: 사용자 행동 기반 영화 추천
    public ResponseEntity<ResponseDto> getActionRecommendationList(int userSeq) {
        // 1. 영화 서버에서 사용자의 최근 행동을 가져옴 (10개)

        // 2. 추천 서버로 사용자의 최근 행동 목록을 전송하고, 추천 영화 목록을 가져옴

        // 3. 해당 영화 번호 목록에 해당 하는 영화 목록을 가져와 반환
        String path = util.getUri("/list/recommendation");
//        return util.sendPostRequest(movieBaseUrl, path, movieSeqList);
        return null;
    }
    
    // TODO: 사용자 평점-리뷰 기반 영화 추천
    public ResponseEntity<ResponseDto> getReviewRecommendationList(int userSeq) {
        // 1. 유저-리뷰 서버에서 사용자의 리뷰 목록을 가져옴

        // 2. 추천 서버로 사용자의 리뷰 목록을 전송하고, Top-N 사용자 리스트를 가져옴

        // 3. 유저-리뷰 서버에서 Top-N 사용자의 리뷰 감성 점수 목록을 가져옴

        // 4. 추천 서버로 사용자의 리뷰 목록과 Top-N 사용자의 리뷰 감성 점수 목록을 전송하고, 추천 영화 목록을 가져옴

        // 5. 해당 영화 번호 목록에 해당 하는 영화 목록을 가져와 반환
        String path = util.getUri("/list/recommendation");
//        return util.sendPostRequest(movieBaseUrl, path, movieSeqList);
        return null;
    }
    
    // TODO: 1일 기준 TOP 10 영화 목록 조회
    public ResponseEntity<ResponseDto> getTopMovieList() {
        // 1. 외부 API 경로 설정
        String path = util.getUri("/list/top10");
        // 2. GET 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendGetRequest(movieBaseUrl, path);
    }
}
