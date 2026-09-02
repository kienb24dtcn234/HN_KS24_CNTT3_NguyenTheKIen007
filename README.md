# Base project — Đề 007: Đặt bàn nhà hàng

Starter gồm Gradle, Spring Boot/Spring AI dependencies, model/repository, corpus và seed data. Các package config, controller, dto, exception, service (rag/chat/langfuse/mcp) và tool chỉ là khung rỗng.

Sinh viên dùng credential cá nhân qua environment variables; không commit secret. Khởi động ứng dụng để JPA tạo bảng, sau đó chạy src/main/resources/seed_data.sql.
## Langfuse

Docker Langfuse được cấu hình sẵn trong `docker-compose-langfuse.yml` và dùng host mặc định `http://localhost:3000`. Sinh viên chỉ cần khởi động Docker, tạo project Langfuse cá nhân rồi điền `LANGFUSE_PUBLIC_KEY` và `LANGFUSE_SECRET_KEY` qua environment.
