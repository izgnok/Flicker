package com.flicker.bff.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flicker.bff.common.module.exception.RestApiException;
import com.flicker.bff.common.module.response.ResponseDto;
import com.flicker.bff.common.module.status.StatusCode;
import com.flicker.bff.dto.movie.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BffMovieService {

    private final Util util; // Util 클래스 의존성 주입

    private final ObjectMapper objectMapper; // ObjectMapper 클래스 의존성 주입

    @Value("${movie.baseurl}")
    private String movieBaseUrl; // 영화 서버 API의 기본 URL

    @Value("${user-review.baseurl}")
    private String userBaseUrl; // 사용자-리뷰 서버 API의 기본 URL

    @Value("${recommend.baseurl}")
    private String recommendBaseUrl; // 추천서버 API의 기본 URL


    public Mono<ResponseEntity<ResponseDto>> createMovie(MovieCreateRequest request) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/create");
        // 2. POST 요청을 비동기적으로 외부 API에 보냅니다.
        return util.sendPostRequestAsync(movieBaseUrl, path, request);
    }

    public Mono<ResponseEntity<ResponseDto>> updateMovie(MovieUpdateRequest request) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/update/detail");
        // 2. 비동기 방식으로 PUT 요청을 외부 API에 보냅니다.
        return util.sendPutRequestAsync(movieBaseUrl, path, request);
    }

    public Mono<ResponseEntity<ResponseDto>> deleteMovie(int movieSeq) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/delete?movieSeq=" + movieSeq);
        // 2. 비동기 방식으로 PUT 요청을 외부 API에 보냅니다.
        return util.sendPutRequestAsync(movieBaseUrl, path, null);
    }

    public Mono<ResponseEntity<ResponseDto>> addActor(ActorAddRequest request) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/add/actor");
        // 2. 비동기 방식으로 POST 요청을 외부 API에 보냅니다.
        return util.sendPostRequestAsync(movieBaseUrl, path, request);
    }

    public Mono<ResponseEntity<ResponseDto>> deleteActor(int actorSeq, int movieSeq) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/delete/actor/" + actorSeq + "/" + movieSeq);
        // 2. 비동기 방식으로 DELETE 요청을 외부 API에 보냅니다.
        return util.sendDeleteRequestAsync(movieBaseUrl, path);
    }


    public Mono<ResponseEntity<ResponseDto>> updateActor(ActorUpdateRequest request) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/admin/update/actor");
        // 2. 비동기 방식으로 PUT 요청을 외부 API에 보냅니다.
        return util.sendPutRequestAsync(movieBaseUrl, path, request);
    }

    public Mono<ResponseEntity<ResponseDto>> getMovieList(int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/" + page + "/" + size);
        // 2. 비동기 방식으로 GET 요청을 외부 API에 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    public Mono<ResponseEntity<ResponseDto>> getMovieListByGenre(String genre, int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/genre/" + genre + "/" + page + "/" + size);
        // 2. 비동기 방식으로 GET 요청을 외부 API에 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    public Mono<ResponseEntity<ResponseDto>> getMovieListByActor(String actorName, int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/actor/" + actorName + "/" + page + "/" + size);
        // 2. 비동기 방식으로 GET 요청을 외부 API에 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    public Mono<ResponseEntity<ResponseDto>> getMovieListByCountry(String country, int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/country/" + country + "/" + page + "/" + size);
        // 2. 비동기 방식으로 GET 요청을 외부 API에 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    public Mono<ResponseEntity<ResponseDto>> getMovieListByYear(int year, int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/year/" + year + "/" + page + "/" + size);
        // 2. 비동기 방식으로 GET 요청을 외부 API에 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    public Mono<ResponseEntity<ResponseDto>> getMovieListBySearch(String keyword, int userSeq, int page, int size) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/search/" + keyword + "/" + userSeq + "/" + page + "/" + size);
        // 2. 비동기 방식으로 GET 요청을 외부 API에 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    public Mono<ResponseEntity<ResponseDto>> getTopMovieList() {
        // 1. 외부 API 경로 설정
        String path = util.getUri("/list/top10");
        // 2. GET 요청 메서드를 사용하여 외부 API에 요청을 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    public Mono<ResponseEntity<ResponseDto>> getMovieWordCloud(int movieSeq) {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/wordCloud/" + movieSeq);
        // 2. 비동기 방식으로 POST 요청을 외부 API에 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    // 영화 상세조회
    public Mono<ResponseEntity<ResponseDto>> getMovieDetail(int movieSeq, int userSeq) {
        try {
            // 0. 응답 객체 생성
            MovieDetailAndReviewAndRecommendResponse movieDetailAndReviewAndRecommendResponse = new MovieDetailAndReviewAndRecommendResponse();
            // 1. 영화 서버에서 영화 상세 조회 결과 가져오기
            String movieDetailPath = util.getUri("/detail/" + movieSeq + "/" + userSeq);
            return util.sendGetRequestAsync(movieBaseUrl, movieDetailPath)
                    .flatMap(getResponse -> {
                        ResponseDto movieDetailResponseDto;
                        try {
                            movieDetailResponseDto = Objects.requireNonNull(getResponse.getBody());
                        } catch (Exception e) {
                            return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "영화 상세정보 Body를 역직렬화하는데 오류 발생: " + e.getMessage()));
                        }
                        // ResponseDto의 상태 코드가 성공이 아닌 경우 처리
                        if (movieDetailResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                            return Mono.error(new RestApiException(
                                    StatusCode.of(movieDetailResponseDto.getHttpStatus(), movieDetailResponseDto.getServiceStatus(), movieDetailResponseDto.getMessage()),
                                    movieDetailResponseDto.getData()
                            ));
                        }
                        // ObjectMapper를 이용하여 JSON 데이터를 MovieDetailResponse로 역직렬화
                        MovieDetailResponse movieDetailResponse;
                        try {
                            movieDetailResponse = objectMapper.convertValue(movieDetailResponseDto.getData(), MovieDetailResponse.class);
                        } catch (Exception e) {
                            return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "영화 상세정보 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                        }
                        movieDetailAndReviewAndRecommendResponse.setMovieDetailResponse(movieDetailResponse);
                        // 2. 추천 서버에서 연관 영화 추천 가져오기
//                        List<RecommendByContentRequest> recommendByContentRequests = Collections.singletonList(new RecommendByContentRequest(movieDetailResponse.getMovieTitle(), movieDetailResponse.getMovieYear(), null));
//                        String recommendationPath = util.getUri("/content");
//                        return util.sendPostRequestToRecommendServer(recommendBaseUrl, recommendationPath, recommendByContentRequests)
//                                .flatMap(recommendResponse -> {
// TODO: 임시
                                    List<MovieSeqListRequest> recommendResponse = new ArrayList<>();
                                    recommendResponse.add(new MovieSeqListRequest("1980", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("가족이라서 문제입니다", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("고스트버스터즈: 오싹한 뉴욕", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("고질라 X 콩: 뉴 엠파이어", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("그녀가 죽었다", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("나쁜 녀석들: 라이드 오어 다이", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("나이트 스윔", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("나이트비치", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("너란 개념", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("눈에 갇힌 외딴 산장에서", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("수유천", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("행복의 나라", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("혹성탈출: 새로운 시대", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("데드풀과 울버린", 2024));
                                    recommendResponse.add(new MovieSeqListRequest("듄: 파트 2", 2024));

                                    // 3. 사용자의 찜/비선호 여부, 비선호 영화 목록, 탑 리뷰 목록을 가져옴
                                    String userMovieDetailPath = util.getUri("/movie-detail?userSeq=" + userSeq + "&movieSeq=" + movieSeq);
                                    return util.sendGetRequestAsync(userBaseUrl, userMovieDetailPath)
                                            .flatMap(userMovieDetailResponse -> {
                                                ResponseDto userMovieDetailDto;
                                                try {
                                                    // JSON 데이터를 ResponseDto로 역직렬화
                                                    userMovieDetailDto = Objects.requireNonNull(userMovieDetailResponse.getBody());
                                                } catch (Exception e) {
                                                    return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "비선호 영화 번호 목록 Body을 역직렬화하는데 오류 발생: " + e.getMessage()));
                                                }
                                                // 상태 코드가 성공이 아닌 경우 처리
                                                if (userMovieDetailDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                                                    return Mono.error(new RestApiException(
                                                            StatusCode.of(userMovieDetailDto.getHttpStatus(), userMovieDetailDto.getServiceStatus(), userMovieDetailDto.getMessage()),
                                                            userMovieDetailDto.getData()
                                                    ));
                                                }
                                                UserMovieDetailResponse userMovieDetail;
                                                try {
                                                    // unlikeResponseDto의 데이터 필드를 List<Integer>로 변환
                                                    userMovieDetail = objectMapper.convertValue(userMovieDetailDto.getData(), UserMovieDetailResponse.class);
                                                } catch (Exception e) {
                                                    return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "사용자 영화 상세 정보 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                                }
                                                // 가져온 목록을 movieDetailReviewRecommendResponse에 설정
                                                movieDetailAndReviewAndRecommendResponse.setBookMarkedMovie(userMovieDetail.isBookMarkedMovie());
                                                movieDetailAndReviewAndRecommendResponse.setUnlikedMovie(userMovieDetail.isUnlikedMovie());
                                                for(ReviewResponse review : userMovieDetail.getReviews()) {
                                                    review.setTop(true);
                                                }
                                                movieDetailAndReviewAndRecommendResponse.setReviews(userMovieDetail.getReviews());
                                                // 4. 추천 영화 목록을 가져와서 영화 서버에 요청
                                                MovieListRequest movieListRequest = new MovieListRequest(recommendResponse, userMovieDetail.getUnlikedMovies());
                                                String movieListPath = util.getUri("/list/recommendation");
                                                return util.sendPostRequestAsync(movieBaseUrl, movieListPath, movieListRequest)
                                                        .flatMap(movieListResponse -> {
                                                            ResponseDto movieListResponseDto;
                                                            try {
                                                                // JSON 데이터를 ResponseDto로 역직렬화
                                                                movieListResponseDto = Objects.requireNonNull(movieListResponse.getBody());
                                                            } catch (Exception e) {
                                                                return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "사용자 상세 조회 연관 추천 영화 목록 Body을 역직렬화하는데 오류 발생: " + e.getMessage()));
                                                            }
                                                            // 상태 코드가 성공이 아닌 경우 처리
                                                            if (movieListResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                                                                return Mono.error(new RestApiException(
                                                                        StatusCode.of(movieListResponseDto.getHttpStatus(), movieListResponseDto.getServiceStatus(), movieListResponseDto.getMessage()),
                                                                        movieListResponseDto.getData()
                                                                ));
                                                            }
                                                            List<MovieListResponse> movieListResponses;
                                                            try {
                                                                // movieListResponseDto의 데이터 필드를 List<MovieListResponse>로 변환
                                                                movieListResponses = objectMapper.convertValue(movieListResponseDto.getData(), new TypeReference<List<MovieListResponse>>() {});
                                                            } catch (Exception e) {
                                                                return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "사용자 상세 조회 연관 추천 영화 목록 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                                            }
                                                            // 변환된 목록을 movieDetailReviewRecommendResponse에 설정
                                                            movieDetailAndReviewAndRecommendResponse.setSimilarMovies(movieListResponses);
                                                            return Mono.just(ResponseDto.response(StatusCode.SUCCESS, movieDetailAndReviewAndRecommendResponse));
                                                        });
//                                            });
                                });
                    })
                    .onErrorResume(e -> {
                        if (e instanceof RestApiException ex) {
                            return Mono.just(ResponseDto.response(ex.getStatusCode(), ex.getData()));
                        } else {
                            return Mono.just(ResponseDto.response(StatusCode.INTERNAL_SERVER_ERROR, "영화 서버에서 상세조회, 추천 서버에서 연관영화를 가져오는데 알 수 없는 오류 발생: " + e.getMessage()));
                        }
                    });
        } catch (Exception e) {
            return Mono.just(ResponseDto.response(StatusCode.UNKNOW_ERROR, "영화 상세 정보 + 연관 영화 + 리뷰 조회 중 알 수 없는 오류가 발생: " + e.getMessage()));
        }
    }


    // 행동 기반 영화 추천
    public Mono<ResponseEntity<ResponseDto>> getActionRecommendationListAsync(int userSeq) {
        // 1. 영화 서버에서 사용자의 최근 행동을 가져옴 (10개)
        String path = util.getUri("/actions/" + userSeq);
        return util.sendGetRequestAsync(movieBaseUrl, path)
                .flatMap(getResponse -> {
                    ResponseDto userActionResponseDto;
                    try {
                        userActionResponseDto = Objects.requireNonNull(getResponse.getBody());
                    } catch (Exception e) {
                        return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "사용자 행동 데이터 Body를 역직렬화하는데 오류 발생: " + e.getMessage()));
                    }
                    // 상태 코드가 성공이 아닌 경우 처리
                    if (userActionResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                        return Mono.error(new RestApiException(
                                StatusCode.of(userActionResponseDto.getHttpStatus(), userActionResponseDto.getServiceStatus(), userActionResponseDto.getMessage()),
                                userActionResponseDto.getData()
                        ));
                    }
                    // 데이터가 List<UserActionResponse> 타입인지 확인 후 변환
                    List<UserActionResponse> userActions;
                    try {
                        userActions = objectMapper.convertValue(userActionResponseDto.getData(), new TypeReference<List<UserActionResponse>>() {});
                    } catch (Exception e) {
                        return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "사용자 행동 데이터 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                    }
                    // 사용자의 최근 행동이 없을 경우 처리
                    if (userActions == null || userActions.isEmpty()) {
                        return Mono.just(ResponseDto.response(StatusCode.NO_CONTENT, "사용자의 최근 행동이 없습니다."));
                    }
                    // 2. 추천 서버로 사용자의 최근 행동 목록을 전송하고, 추천 영화 목록을 가져옴
                    String recommendationPath = util.getUri("/content");
                    // 추천 서버에 요청할 때 사용자의 최근 행동 키워드만 추출하여 전송
                    List<RecommendByContentRequest> recommendByContentRequests = userActions.stream()
                            .map(userAction -> new RecommendByContentRequest(userAction.getKeyword(), userAction.getMovieYear(), null))
                            .toList();
                    return util.sendPostRequestToRecommendServer(recommendBaseUrl, recommendationPath, recommendByContentRequests)
                            .flatMap(recommendResponse -> {
                                // 3. 사용자의 비선호 영화 목록을 가져옴
                                String unlikeMoviePath = util.getUri("/" + userSeq + "/unlike-movie");
                                return util.sendGetRequestAsync(userBaseUrl, unlikeMoviePath)
                                        .flatMap(unlikeResponse -> {
                                            ResponseDto unlikeResponseDto;
                                            try {
                                                // JSON 데이터를 ResponseDto로 역직렬화
                                                unlikeResponseDto = Objects.requireNonNull(unlikeResponse.getBody());
                                            } catch (Exception e) {
                                                return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "비선호 영화 번호 목록 Body을 역직렬화하는데 오류 발생: " + e.getMessage()));
                                            }
                                            // 상태 코드가 성공이 아닌 경우 처리
                                            if (unlikeResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                                                return Mono.error(new RestApiException(
                                                        StatusCode.of(unlikeResponseDto.getHttpStatus(), unlikeResponseDto.getServiceStatus(), unlikeResponseDto.getMessage()),
                                                        unlikeResponseDto.getData()
                                                ));
                                            }
                                            List<Integer> unlikeMovieSeqs;
                                            try {
                                                // unlikeResponseDto의 데이터 필드를 List<Integer>로 변환
                                                unlikeMovieSeqs = objectMapper.convertValue(unlikeResponseDto.getData(), new TypeReference<List<Integer>>() {});
                                            } catch (Exception e) {
                                                return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "비선호 영화 번호 목록 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                            }
                                            // 4. 추천 영화 목록을 가져와서 영화 서버에 요청
                                            MovieListRequest movieListRequest = new MovieListRequest(recommendResponse, unlikeMovieSeqs);
                                            String movieListpath = util.getUri("/list/recommendation");
                                            return util.sendPostRequestAsync(movieBaseUrl, movieListpath, movieListRequest)
                                                    .flatMap(movieListResponse -> {
                                                        ResponseDto movieListResponseDto;
                                                        try {
                                                            // JSON 데이터를 ResponseDto로 역직렬화
                                                            movieListResponseDto = Objects.requireNonNull(movieListResponse.getBody());
                                                        } catch (Exception e) {
                                                            return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "행동 기반 추천 영화 목록 Body를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                                        }
                                                        // 상태 코드가 성공이 아닌 경우 처리
                                                        if (movieListResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                                                            return Mono.error(new RestApiException(
                                                                    StatusCode.of(movieListResponseDto.getHttpStatus(), movieListResponseDto.getServiceStatus(), movieListResponseDto.getMessage()),
                                                                    movieListResponseDto.getData()
                                                            ));
                                                        }
                                                        List<MovieListResponse> movieListResponses;
                                                        try {
                                                            // movieListResponseDto의 데이터 필드를 List<MovieListResponse>로 변환
                                                            movieListResponses = objectMapper.convertValue(movieListResponseDto.getData(), new TypeReference<List<MovieListResponse>>() {});
                                                        } catch (Exception e) {
                                                            return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "행동 기반 추천 영화 목록 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                                        }
                                                        // 변환된 목록을 반환
                                                        return Mono.just(ResponseDto.response(StatusCode.SUCCESS, movieListResponses));
                                                    });
                                        });
                            });
                }).onErrorResume(e -> {
                    if (e instanceof RestApiException ex) {
                        return Mono.just(ResponseDto.response(ex.getStatusCode(), ex.getData()));
                    } else {
                        return Mono.just(ResponseDto.response(StatusCode.UNKNOW_ERROR, "영화 서버에서 행동 기반 추천을 가져오는데 알 수 없는 오류 발생: " + e.getMessage()));
                    }
                });
    }


    // 사용자 평점-리뷰 기반 영화 추천
    public Mono<ResponseEntity<ResponseDto>> getReviewRecommendationList(int userSeq) {
        // 1. 추천서버에서 추천 영화 목록을 가져옴
        String path = util.getUri("/collabo");
        return util.sendPostRequestToRecommendServer(recommendBaseUrl, path, userSeq)
                .flatMap(recommendResponse -> {
                    // 2. 사용자 서버에서 비선호 영화 목로 가져옴
                    String unlikeMoviePath = util.getUri("/" + userSeq + "/unlike-movie");
                    return util.sendGetRequestAsync(userBaseUrl, unlikeMoviePath)
                            .flatMap(unlikeResponse -> {
                                ResponseDto unlikeResponseDto;
                                try {
                                    // JSON 데이터를 ResponseDto로 역직렬화
                                    unlikeResponseDto = Objects.requireNonNull(unlikeResponse.getBody());
                                } catch (Exception e) {
                                    return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "비선호 영화 번호 목록 Body을 역직렬화하는데 오류 발생: " + e.getMessage()));
                                }
                                // 상태 코드가 성공이 아닌 경우 처리
                                if (unlikeResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                                    return Mono.error(new RestApiException(
                                            StatusCode.of(unlikeResponseDto.getHttpStatus(), unlikeResponseDto.getServiceStatus(), unlikeResponseDto.getMessage()),
                                            unlikeResponseDto.getData()
                                    ));
                                }
                                List<Integer> unlikeMovieSeqs;
                                try {
                                    // unlikeResponseDto의 데이터 필드를 List<Integer>로 변환
                                    unlikeMovieSeqs = objectMapper.convertValue(unlikeResponseDto.getData(), new TypeReference<List<Integer>>() {
                                    });
                                } catch (Exception e) {
                                    return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "비선호 영화 번호 목록 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                }
                                // 3. 추천 영화 목록을 가져와서 영화 서버에 요청
                                MovieListRequest movieListRequest = new MovieListRequest(recommendResponse, unlikeMovieSeqs);
                                String movieListPath = util.getUri("/list/recommendation");
                                return util.sendPostRequestAsync(movieBaseUrl, movieListPath, movieListRequest)
                                        .flatMap(movieListResponse -> {
                                            ResponseDto movieListResponseDto;
                                            try {
                                                // JSON 데이터를 ResponseDto로 역직렬화
                                                movieListResponseDto = Objects.requireNonNull(movieListResponse.getBody());
                                            } catch (Exception e) {
                                                return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "리뷰 기반 추천 영화 목록 Body를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                            }
                                            // 상태 코드가 성공이 아닌 경우 처리
                                            if (movieListResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                                                return Mono.error(new RestApiException(
                                                        StatusCode.of(movieListResponseDto.getHttpStatus(), movieListResponseDto.getServiceStatus(), movieListResponseDto.getMessage()),
                                                        movieListResponseDto.getData()
                                                ));
                                            }
                                            List<MovieListResponse> movieListResponses;
                                            try {
                                                // movieListResponseDto의 데이터 필드를 List<MovieListResponse>로 변환
                                                movieListResponses = objectMapper.convertValue(movieListResponseDto.getData(), new TypeReference<List<MovieListResponse>>() {});
                                            } catch (Exception e) {
                                                return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "리뷰 기반 추천 영화 목록 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                            }
                                            return Mono.just(ResponseDto.response(StatusCode.SUCCESS, movieListResponses));
                                        });
                            });
                }).onErrorResume(e -> {
                    if (e instanceof RestApiException ex) {
                        return Mono.just(ResponseDto.response(ex.getStatusCode(), ex.getData()));
                    } else {
                        return Mono.just(ResponseDto.response(StatusCode.UNKNOW_ERROR, "영화 서버에서 리뷰 기반 추천을 가져오는데 알 수 없는 오류 발생: " + e.getMessage()));
                    }
                });
    }

    public Mono<ResponseEntity<ResponseDto>> getTopRatingMovieList() {
        // 1. 외부 API의 경로를 설정합니다.
        String path = util.getUri("/list/topRating");
        // 2. 비동기 방식으로 GET 요청을 외부 API에 보냅니다.
        return util.sendGetRequestAsync(movieBaseUrl, path);
    }

    public Mono<ResponseEntity<ResponseDto>> getRecommendationMovieListByActor(int userSeq) {
        // 1. 영화 서버에서 사용자의 추천 영화 배우 가져오기
        String path = util.getUri("/list/recommendActor/" + userSeq);
        return util.sendGetRequestAsync(movieBaseUrl, path)
                .flatMap(getResponse -> {
                    ResponseDto actorResponseDto;
                    try {
                        actorResponseDto = Objects.requireNonNull(getResponse.getBody());
                    } catch (Exception e) {
                        return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "추천 배우 목록 Body를 역직렬화하는데 오류 발생: " + e.getMessage()));
                    }
                    // 상태 코드가 성공이 아닌 경우 처리
                    if (actorResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                        return Mono.error(new RestApiException(
                                StatusCode.of(actorResponseDto.getHttpStatus(), actorResponseDto.getServiceStatus(), actorResponseDto.getMessage()),
                                actorResponseDto.getData()
                        ));
                    }
                    String actorName;
                    try {
                        // actorListResponseDto의 데이터 필드를 String로 변환
                        actorName = objectMapper.convertValue(actorResponseDto.getData(), new TypeReference<String>() {
                        });
                    } catch (Exception e) {
                        return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "추천 배우 목록 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                    }
                    if(actorName == null || actorName.isEmpty()) {
                        return Mono.just(ResponseDto.response(StatusCode.NO_CONTENT, "최근에 리뷰를 달지 않았습니다."));
                    }
                    // 2. 추천 서버에서 연관 영화 목록을 가져옴
                    List<RecommendByContentRequest> recommendByContentRequests = Collections.singletonList(new RecommendByContentRequest(null, null, actorName));
                    String recommendationPath = util.getUri("/content");
                    return util.sendPostRequestToRecommendServer(recommendBaseUrl, recommendationPath, recommendByContentRequests)
                            .flatMap(recommendResponse -> {
                                // 3. 사용자의 비선호 영화 목록을 가져옴
                                String unlikeMoviePath = util.getUri("/" + userSeq + "/unlike-movie");
                                return util.sendGetRequestAsync(userBaseUrl, unlikeMoviePath)
                                        .flatMap(unlikeResponse -> {
                                            ResponseDto unlikeResponseDto;
                                            try {
                                                // JSON 데이터를 ResponseDto로 역직렬화
                                                unlikeResponseDto = Objects.requireNonNull(unlikeResponse.getBody());
                                            } catch (Exception e) {
                                                return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "비선호 영화 번호 목록 Body을 역직렬화하는데 오류 발생: " + e.getMessage()));
                                            }
                                            // 상태 코드가 성공이 아닌 경우 처리
                                            if (unlikeResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                                                return Mono.error(new RestApiException(
                                                        StatusCode.of(unlikeResponseDto.getHttpStatus(), unlikeResponseDto.getServiceStatus(), unlikeResponseDto.getMessage()),
                                                        unlikeResponseDto.getData()
                                                ));
                                            }
                                            List<Integer> unlikeMovieSeqs;
                                            try {
                                                // unlikeResponseDto의 데이터 필드를 List<Integer>로 변환
                                                unlikeMovieSeqs = objectMapper.convertValue(unlikeResponseDto.getData(), new TypeReference<List<Integer>>() {
                                                });
                                            } catch (Exception e) {
                                                return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "비선호 영화 번호 목록 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                            }
                                            // 4. 추천 영화 목록을 가져와서 영화 서버에 요청
                                            MovieListRequest movieListRequest = new MovieListRequest(recommendResponse, unlikeMovieSeqs);
                                            String movieListPath = util.getUri("/list/recommendation");
                                            return util.sendPostRequestAsync(movieBaseUrl, movieListPath, movieListRequest)
                                                    .flatMap(movieListResponse -> {
                                                        ResponseDto movieListResponseDto;
                                                        try {
                                                            // JSON 데이터를 ResponseDto로 역직렬화
                                                            movieListResponseDto = Objects.requireNonNull(movieListResponse.getBody());
                                                        } catch (Exception e) {
                                                            return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "추천 배우 기반 영화 목록 Body를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                                        }
                                                        // 상태 코드가 성공이 아닌 경우 처리
                                                        if (movieListResponseDto.getServiceStatus() != StatusCode.SUCCESS.getServiceStatus()) {
                                                            return Mono.error(new RestApiException(
                                                                    StatusCode.of(movieListResponseDto.getHttpStatus(), movieListResponseDto.getServiceStatus(), movieListResponseDto.getMessage()),
                                                                    movieListResponseDto.getData()
                                                            ));
                                                        }
                                                        List<MovieListResponse> movieListResponses;
                                                        try {
                                                            // movieListResponseDto의 데이터 필드를 List<MovieListResponse>로 변환
                                                            movieListResponses = objectMapper.convertValue(movieListResponseDto.getData(), new TypeReference<List<MovieListResponse>>() {
                                                            });
                                                        } catch (Exception e) {
                                                            return Mono.error(new RestApiException(StatusCode.INTERNAL_SERVER_ERROR, "추천 배우 기반 영화 목록 데이터를 역직렬화하는데 오류 발생: " + e.getMessage()));
                                                        }
                                                        return Mono.just(ResponseDto.response(StatusCode.SUCCESS, movieListResponses));
                                                    });
                                        });
                            });
                }).onErrorResume(e -> {
                    if (e instanceof RestApiException ex) {
                        return Mono.just(ResponseDto.response(ex.getStatusCode(), ex.getData()));
                    } else {
                        return Mono.just(ResponseDto.response(StatusCode.UNKNOW_ERROR, "영화 서버에서 배우 기반 영화 추천 목록 가져오는데 알 수 없는 오류 발생: " + e.getMessage()));
                    }
                });
    }
}
