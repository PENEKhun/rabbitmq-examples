# 메세지큐를 직접 써볼거임.

1. RabbitMQ

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=guest \
  -e RABBITMQ_DEFAULT_PASS=guest \
  rabbitmq:3-management
```

## TODO

- Outbox
  - Outbox 패턴을 적용해서, 트랜잭션이 성공하면 메시지를 발행하는 구조로 만들기
  - (별도의 테이블을 만들어서, 메시지를 저장하고, CDC로 RabbitMQ로 발행하는 구조)

## 만드려는 것 : 회원가입

- 회원가입에 성공하면,
  - 사용자에게 메일 보내고, (SMTP, JavaMailSender)
  - 이벤트성 쿠폰을 발급하고,
  - 사용자 증감 통계 갱신 이벤트 발행 (될진 모르겠지만 : 이건 여러개 쌓여도 하나만 발행되도록)
