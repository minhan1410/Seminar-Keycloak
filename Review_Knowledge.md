# Nhắc lại kiến thức

## Oauth2

### Định nghĩa

- OAuth2 là một phương thức chứng thực giúp các ứng dụng có thể chia sẻ tài nguyên với nhau mà không cần chia sẻ thông tin **username** và **password**

### Các thành phần, cách hoạt động

- Thành phần:

  - **Resource Owner**: người sở hữu tài nguyên(VD: người dùng)

  - **Resource Server**: máy chủ chứa tài nguyên(VD: FB, Google,...)

  - **Authorization Server**: máy chủ cung cấp quyền truy cập vào tài nguyên(VD: FB, Google,...)

  - **Client**: ứng dụng yêu cầu truy cập vào tài nguyên(VD: ứng dụng của bạn)

- Cách hoạt động:

    ![oauth2Flow](https://images.viblo.asia/4b4ca6c8-c55d-4c45-9301-8b7f312fbee5.png)

    1. **Application** yêu cầu ủy quyền để truy cập vào **Resource Server** thông qua **User**

    1. Nếu **User** ủy quyền cho yêu cầu trên, **Application** sẽ nhận được giấy ủy quyền từ phía **User** (dưới dạng một **token string** nào đó chẳng hạn)

    1. **Application** gửi thông tin định danh (ID) của mình kèm theo giấy ủy quyền của **User** tới **Authorization Server**

    1. Nếu thông tin định danh được xác thực và giấy ủy quyền hợp lệ, **Authorization** Server sẽ trả về cho **Application** **access_token**. Đến đây quá trình ủy quyền hoàn tất. Để truy cập vào tài nguyên (resource) từ **Resource Server** và lấy thông tin, **Application** sẽ phải đưa ra **access_token** để xác thực.

    1. Nếu **access_token** hợp lệ, **Resource Server** sẽ trả về dữ liệu của tài nguyên đã được yêu cầu cho **Application**.

### Authorization Grant

- Có 4 loại Authorization Grant:

  - **Authorization Code Grant**: sử dụng với các server-side Application.

  - **Implicit Grant**: sử dụng với các client-side Application.

  - **Resource Owner Password Credentials Grant**: sử dụng với các Trusted Application, kiểu như những ứng dụng của chính Service.

  - **Client Credentials Grant**: sử dụng với các ứng dụng truy cập thông qua API.

- **Authorization Code Grant**

    ![authorizationCodeGrant](https://images.viblo.asia/81f121c0-56df-46d1-9377-40818a7eba72.png)

    1. **Application** chuyển hướng **User** đến **Authorization Server** với yêu cầu ủy quyền.

    1. **User** đăng nhập và chấp nhận yêu cầu ủy quyền.

    1. **Authorization Server** chuyển hướng **User** trở lại **Application** với một **authorization code**.

    1. **Application** gửi **authorization code** đến **Authorization Server** để yêu cầu **access token**.

    1. **Authorization Server** xác thực và trả về **access token** cho **Application**.

    1. **Application** sử dụng **access token** để truy cập tài nguyên từ **Resource Server**.

- **Implicit Grant**

    ![implicitGrant](https://images.viblo.asia/e562096d-ce3c-497e-99ea-df030a61a3fa.png)

    1. **Application** chuyển hướng **User** đến **Authorization Server** với yêu cầu ủy quyền.

    1. **User** đăng nhập và chấp nhận yêu cầu ủy quyền.

    1. **Browser** nhận **access_token** thông qua **Redirect URI**

    1. **Browser** duy trì **access_token**

    1. **Application** trích xuất **access_token**

    1. **access_token** được gửi đến **Application**

    **Lưu ý: Không hỗ trợ `refresh_token`**

- **Resource Owner Password Credentials Grant**

    1. **User** cung cấp thông tin đăng nhập (username và password) cho **Application**.

    1. **Application** gửi thông tin đăng nhập này đến **Authorization Server** để yêu cầu **access token**.

    1. **Authorization Server** xác thực thông tin đăng nhập.

    1. Nếu thông tin hợp lệ, **Authorization Server** trả về **access token** cho **Application**.

    1. **Application** sử dụng **access token** để truy cập tài nguyên từ **Resource Server**.

    **Lưu ý: Quy trình này đơn giản nhưng yêu cầu sự tin tưởng cao từ phía người dùng, vì thông tin đăng nhập được chia sẻ trực tiếp với ứng dụng.**

- **Client Credentials Grant**

    1. **Client** gửi yêu cầu đến **Authorization Server** với thông tin định danh của mình (`client ID` và `client secret`).

    1. **Authorization Server** xác thực thông tin định danh của **Client**.

    1. Nếu thông tin hợp lệ, **Authorization Server** trả về **access token** cho **Client**.

    1. **Client** sử dụng **access token** để truy cập tài nguyên từ **Resource Server**.

    **Lưu ý: Quy trình này thường được sử dụng cho các ứng dụng cần truy cập vào API mà không cần sự can thiệp của người dùng, như các dịch vụ nền tảng hoặc các tác vụ tự động.**

## OpenID Connect(OIDC)

### Định nghĩa

- **OICD** hoạt động trên nền tảng của **Oauth2**, cung cấp thêm chức năng authen user.

### Cách hoạt động

 ![OidcFlow](https://miro.medium.com/v2/resize:fit:720/format:webp/1*1McvnvrW6wh37ECYpmTSxw.png)

1. User bấm đăng nhập

1. App tạo yêu cầu xác thực(**authentication request**)

1. Yêu cầu xác thực này được chuyển đến **Authorization Server (Keycloak)** và người dùng được chuyển hướng đến giao diện login của **Keycloak**.

1. Gửi form login lên **Keycloak**

1. **Keycloak** xác thực thông tin đăng nhập của người dùng

1. Nếu xác thực thành công, **Keycloak** tạo ra một **`authorization code`** và trả về cho App thông qua redirect

1. App sử dụng **`authorization code`** để gửi yêu cầu đến **Authorization Server**, đổi lấy **ID Token, Access Token, và Refresh Token**. Trong đó:

    - **ID Token**: Là **JWT** chứa thông tin về danh tính người dùng và được dùng để xác thực người dùng

    - **Access Token**: Dùng để truy cập tài nguyên trên **Resource Server**

    - **Refresh Token**: Dùng để yêu cầu tạo mới **Access Token** khi **Access Token** hết hạn

### So sánh OIDC vs Oauth2

- So sánh:

    |  | Oauth2 | OIDC |
    |---|:---:|:---:|
    | **Mục đích** | Chỉ tập trung vào việc **author** | Kết hợp của **Oauth2 + authen** |
    | **Các token chính** | Chỉ có **Access Token** để truy cập tài nguyên trên **Resource Server** | Có **ID Token, Access Token, và Refresh Token**. Trong đó:<br><br>- **ID Token**: Là **JWT** chứa thông tin về danh tính người dùng và được dùng để xác thực người dùng<br>- **Access Token**: Dùng để truy cập tài nguyên trên **Resource Server**<br>-**Refresh Token**: Dùng để yêu cầu tạo mới **Access Token** khi **Access Token** hết hạn |
    | **Scopes** | Yêu cầu quyền truy cập vào tài nguyên mà ứng dụng cần để thực hiện chức năng của mình(**read, write, delete**) | Có thêm 1 số scope khác để yêu cầu thông tin người dùng cụ thể từ authorization server(**openid, profile, email, address**) |

- Khi đọc về So sánh OIDC vs Oauth2 trên mạng, tôi khá lơ mơ và đặt ra câu hỏi như sau:

    ```text
    Câu hỏi:
        - Nếu tôi có ứng dụng tích hợp signin GG, lấy thông tin profile của GG để tạo user thì hiển nhiên đó là OIDC.

        - Nếu ứng dụng khác có chức năng quản lý ảnh tích hợp signin GG để lấy ảnh trong đó thì sẽ là Oauth2.

        - Nhưng nếu tôi dùng Oauth2 với scope = profile thì nó vẫn có thể lấy được thông tin người dùng để tạo tk. Vậy điểm khác biệt của nó vs OIDC là gì?
    
    Trả lời:
    ```

## SSO

## Tham khảo

- [Introduction to Oauth2](https://viblo.asia/p/introduction-to-oauth2-3OEqGjDpR9bL)

- [Tìm hiểu về OAuth2, OpenID Connect, SAML và Kerberos](https://viblo.asia/p/tim-hieu-ve-oauth2-openid-connect-saml-va-kerberos-cac-phuong-thuc-xac-thuc-va-uy-quyen-trong-ung-dung-hien-dai-GAWVpx2oV05)
