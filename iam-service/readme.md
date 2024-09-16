# Sử dụng phân quyền trong file policy.json

- Bât cấu hình **Authorization** trong **Capability config** sẽ xuất hiện tab mới **Authorization**, sau đó cấu hình các **Resource, Policies, Permissions** và kiểm tra lại ở tab **Evaluate**

## Mô tả

- Phân quyền trực tiếp trên keycloak

- Quản lý user, role trong keycloak thông qua rest api

## Hướng dẫn

## Cấu hình

- Chạy file `docker-compose.yml` để tự động import realm và các config vào Keycloak.

- Truy cập `http://localhost:8181/` chọn realm `Seminar`

    ![img](../img/image.png)

- Cấu hình **Resource, Policies, Permissions**

    ![img4](../img/image4.png)

    ![img5](../img/image5.png)

    ![img6](../img/image6.png)

- Tạo user, phân quyền

    ![img10](../img/image10.png)

    ![img13](../img/image13.png)

    ![img11](../img/image11.png)

    ![img9](../img/image9.png)

- **Evaluate**

  - Thử **User:** `user` với **Role:** `User Role`, ta thấy với **User resource** `user` có quyền truy cập được, còn **Admin resource** thì không

    ![img7](../img/image7.png)

    ![img8](../img/image8.png)

- Cấu hình api gửi email, điền lại username, password

    ![img12](../img/image12.png)

## Chạy project

- **Regerate** lại **Clinet Secret** của 2 clients `book-service` và `iam-service`

    ![img1](../img/image1.png)

- Sau đó **replace** lại các **client secret** trong file `application.properties` và `policy-enforcer.json` và chạy project

    ![img2](../img/image2.png)

- Truy cập `http://localhost:8080/swagger-ui/index.html#` để truy cập vào ứng dụng

    ![img3](../img/image3.png)
