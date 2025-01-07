# WebSocket Messaging Service

>  Spring Boot를 기반으로 한 WebSocket 메시징 서비스


### **참고 자료**

<details>
<summary>Click to expand</summary>

- [Spring Messaging STOMP WebSocket Guide](https://spring.io/guides/gs/messaging-stomp-websocket)
- [GitHub: gs-messaging-stomp-websocket](https://github.com/spring-guides/gs-messaging-stomp-websocket)

</details>



## Dependency

- Spring Web
- WebSocket


## **WebSocket 연결**

### **WebSocket 엔드포인트**
- **URL**: `ws://localhost:8080/ws`
- **프로토콜**: WebSocket

### **WebSocket 연결 요청 및 응답**

#### **요청**
 ```http
 GET /ws HTTP/1.1
 Host: localhost:8080
 Upgrade: websocket
 Connection: Upgrade
 Sec-WebSocket-Key: x3JJHMbDL1EzLkh9GBhXDw==
 Sec-WebSocket-Version: 13
 ```

#### **응답**
 ```http
 HTTP/1.1 101 Switching Protocols
 Upgrade: websocket
 Connection: Upgrade
 Sec-WebSocket-Accept: HSmrc0sMlYUkAGmm5OPpG2HaGWk=
 ```


## **메시지 처리**

> STOMP 프로토콜 사용

### **메시지 요청**
- **발행 엔드포인트**: `/app/sendMessage`
- **Content-Type**: `application/json`

#### **요청 예시 (JSON)**
 ```json
 {
   "content": "안녕하세요!"
 }
 ```

#### **Curl 명령어 예시**
 ```bash
 curl -X POST \
 -H "Content-Type: application/json" \
 -d '{
   "content": "안녕하세요!"
 }' \
 http://localhost:8080/app/sendMessage
 ```


### **메시지 응답**
- **구독 엔드포인트**: `/topic/receiveMessage`
- **Content-Type**: `application/json`

#### **응답 예시 (JSON)**
 ```json
 {
   "message": "안녕하세요!",
 }
 ```


## **WebSocket 클라이언트 예시**

### **JavaScript 코드 (Stomp.js 사용)**
```javascript
// Websocket Connection 엔드포인트 설정
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

// 메세지 수신, 구독 ( Subscribed )
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/receiveMessage', (message) => {
        showMessages(JSON.parse(message.body).content);
    });
};

// 오류 처리 
stompClient.onWebSocketError = (error) => {
    console.error('WebSocket Error:', error);
};

stompClient.onStompError = (frame) => {
    console.error('STOMP Error:', frame.headers['message']);
};

// Websocket 연결
function connect() {
    stompClient.activate();
}

// Websocket 연결 해제
function disconnect() {
    stompClient.deactivate();
    console.log('Disconnected');
}

// 메세지 전송, 발생 ( Publish ) 
function sendMessage() {
    stompClient.publish({
        destination: "/app/sendMessage",
        body: JSON.stringify({ content: $("#name").val() })
    });
}

// 응답값에 맞는 HTML 추가 
function showMessages(message) {
    $("#messages").append(`<tr><td>${message}</td></tr>`);
}

$(function () {
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});
```



## **실습**

1. **Spring Boot 서버 시작하기** (Gradle 사용):
    ```bash
    ./gradlew bootRun
    ```

2. **위 엔드포인트를 사용하여 WebSocket 연결**

3. **Curl 또는 WebSocket 클라이언트를 사용해 메시지 테스트**


## **에러 처리**

### **자주 발생하는 에러**
- **MessageConversionException**:
  요청 본문이 DTO 구조와 일치하는지 확인 필요

- **Connection Refused**:
  WebSocket 서버가 실행 중이며 접근 가능한지 확인 필요

<br/>
