# Response Structure
    기본적인 응답 구조는 다음과 같습니다.
    1. statusCode - 상태코드
    2. message - 응답 메시지
    3. data - 결과물
    4. context - 부가적인 정보들

# 관리자 API 
## request : /api/admin/
***
## 1. 강의 API 
### request : /api/admin/courses
***

### 1. 강의목록조회 - GET /api/admin/courses?page=1
※ [CourseResponseDTO](#courseresponsedto--강의정보-)

    ** RequestParam **
    page - 페이지번호 / 범위=[1,infinite]
      
    ** Response **
    MediaType : JSON
    statusCode - 200
    message - 강의 목록 조회
    data - 강의 목록 (Array of CourseResponseDTO)
    context
        .nowPage - 현재 읽은 페이지
        .maxPage - 마지막 페이지
### 2. 강의 조회 - GET /api/admin/courses/{courseId}
※ [CourseResponseDTO](#courseresponsedto--강의정보-)

    **RequestParam**
    page - 페이지번호 / 범위=[1,infinite]
      
    **Response**
    MediaType : JSON
    statusCode - 200
    message - 강의 목록 조회
    data - 강의정보 (CourseResponseDTO)

### 3. 강의 추가 - POST /api/admin/courses/add
※ [시간표](#시간표)
### RequestBody  
| param | description | Type       | required |   
|-----|-------------|------------|----------|  
| name | 강의명         | String(25) | true     |  
| roomId| 강의실id       | Long       | true     |
| departmentId| 학과id| Long       |true|
| professorId| 교수id| Long       |true|
| maxNum| 최대수강인원| Integer    |true|
|courseScheduleDTO|시간표| List<시간표>  |true|

### Response / FAIL
    **Response**
    MediaType : JSON
    statusCode - 400
    message - 강의 생성 실패

### Response / SUCCESS
    **Response**
    MediaType : JSON
    statusCode - 200
    message - 강의 생성
    data - 생성된 강의정보(CourseResponseDTO)

***
***
# Param 정보
***

### CourseResponseDTO(강의정보)
    courseId - 강의 id 
    name - 강의명
    room - 강의실
    id - 강의실 id
    location - 강의실명
    ho - 강의실 호
    department - 개설학과명 
    id - 학과 id
    name - 학과명
    enrollNum - 현재수강인원
    maxNum - 최대수강인원
    professorId - 강의교수 id
    professorName - 강의 교수명


### 시간표  
※ [Day](#day)  

| param           | description | Type    | required |   
|-----------------|-------------|---------|----------|
| id              | 시간표id       | Long    |false|
| courseDay       | 강의일         | Day     |true|
| courseHourStart | 강의시작시간(시)   | Integer |true
| courseMinStart  | 강의시작시간(분)   | Integer |true
| courseHourEnd   | 강의종료시간(시)   | Integer |true
| courseMinEnd    | 강의종료시간(분)   | Integer |true

### Day
| Value | description|
|----|----|
|SUN|일요일|
|MON| 월요일|
|TUE|화요일|
|WED|수요일|
|THU|목요일|
|FRI|금요일|
|SAT|토요일|


    



