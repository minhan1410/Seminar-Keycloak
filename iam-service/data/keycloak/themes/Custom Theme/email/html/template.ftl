<#macro emailLayout>
    <html>
    <body>
    <div>
        <img class="logo" src="https://media.licdn.com/dms/image/v2/D560BAQGuDzBofp9hVA/company-logo_200_200/company-logo_200_200/0/1695311338966?e=1733961600&v=beta&t=Nq-ABAE7wzzaiNCUDA_faAz9FpZoDiw6hAAjzKc7Et8" alt="IntrustCA" style="width: 200px;">
    </div>
    <#nested>
    <footer>
        <div>
            <p class="copyright">&copy; Minh An tập custom email page for Keycloak. Copy của <a
                        href="https://github.com/p2-inc/keycloak-email-theme-template?tab=readme-ov-file">p2-inc</a></p>
        </div>
    </footer>
    </body>
    </html>
</#macro>