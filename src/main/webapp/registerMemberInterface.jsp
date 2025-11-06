<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký thành viên - Siêu thị điện máy</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
        .container { max-width: 520px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 16px; }
        label { display: block; margin-bottom: 6px; font-weight: bold; }
        input[type="text"], input[type="date"], input[type="password"], textarea, select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        textarea { resize: vertical; min-height: 80px; }
        .btn { background-color: #28a745; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; width: 100%; }
        .btn:hover { background-color: #218838; }
        .error { color: #dc3545; margin-bottom: 12px; }
        .hint { color: #6c757d; font-size: 12px; }
        .links { text-align: center; margin-top: 20px; }
        .links a { color: #007bff; text-decoration: none; }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }

        /* Modal popup */
        .modal-overlay { position: fixed; inset: 0; background: rgba(0,0,0,0.4); display: none; align-items: center; justify-content: center; z-index: 1000; }
        .modal { background: #fff; width: 90%; max-width: 420px; border-radius: 8px; box-shadow: 0 10px 25px rgba(0,0,0,0.2); overflow: hidden; }
        .modal-header { padding: 14px 16px; background: #28a745; color: #fff; font-weight: bold; }
        .modal-body { padding: 16px; color: #333; }
        .modal-footer { padding: 12px 16px; text-align: right; background: #f7f7f7; }
        .modal-btn { background: #28a745; color: #fff; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; }
        .modal-btn:hover { background: #218838; }
    </style>
</head>
<body>
    <div>
        <h2>Đăng ký thành viên hệ thống</h2>

        <div style="margin-bottom: 16px;">
            <a href="${pageContext.request.contextPath}/" class="btn back-btn">← Quay lại</a>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        <% if (request.getAttribute("success") != null) { %>
            <span id="successMsg" style="display:none;"><%= request.getAttribute("success") %></span>
        <% } %>

        <form id="registerForm" method="post" action="${pageContext.request.contextPath}/register" novalidate>
            <div class="form-group">
                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Xác nhận mật khẩu</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <div id="pwError" class="error" style="display:none;">Mật khẩu không khớp.</div>
            </div>

            <div class="form-group">
                <label for="fullname">Họ và tên</label>
                <input type="text" id="fullname" name="fullname" required>
            </div>

            <div class="form-group">
                <label for="dateOfBirth">Ngày sinh</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" required>
            </div>

            <div class="form-group">
                <label for="gender">Giới tính</label>
                <select id="gender" name="gender" required>
                    <option value="male">Nam</option>
                    <option value="female">Nữ</option>
                </select>
            </div>

            <div class="form-group">
                <label for="phone">Số điện thoại</label>
                <input type="text" id="phone" name="phone" required>
            </div>

            <div class="form-group">
                <label for="address">Địa chỉ</label>
                <textarea id="address" name="address" required></textarea>
            </div>

            <button id="submitBtn" type="submit" class="btn">Đăng ký</button>
        </form>
    </div>
    <!-- Modal structure -->
    <div id="modalOverlay" class="modal-overlay">
        <div class="modal">
            <div class="modal-header">Thông báo</div>
            <div class="modal-body" id="modalMessage">Thao tác thành công.</div>
            <div class="modal-footer">
                <button id="modalOkBtn" type="button" class="modal-btn">OK</button>
            </div>
        </div>
    </div>
    <script>
        (function() {
            var password = document.getElementById('password');
            var confirmPassword = document.getElementById('confirmPassword');
            var pwError = document.getElementById('pwError');
            var submitBtn = document.getElementById('submitBtn');
            var form = document.getElementById('registerForm');
            var successMsgEl = document.getElementById('successMsg');
            var modalOverlay = document.getElementById('modalOverlay');
            var modalMessage = document.getElementById('modalMessage');
            var modalOkBtn = document.getElementById('modalOkBtn');

            function validatePasswordMatch() {
                var isMismatch = password.value !== confirmPassword.value;
                if (confirmPassword.value.length === 0) {
                    pwError.style.display = 'none';
                    submitBtn.disabled = false;
                    return;
                }
                pwError.style.display = isMismatch ? 'block' : 'none';
                submitBtn.disabled = isMismatch;
            }

            password.addEventListener('input', validatePasswordMatch);
            confirmPassword.addEventListener('input', validatePasswordMatch);

            form.addEventListener('submit', function(e) {
                if (password.value !== confirmPassword.value) {
                    e.preventDefault();
                    pwError.style.display = 'block';
                }
            });

            function showModal(message) {
                if (message) {
                    modalMessage.textContent = message;
                }
                modalOverlay.style.display = 'flex';
            }

            function hideModal() {
                modalOverlay.style.display = 'none';
            }

            if (successMsgEl && successMsgEl.textContent && successMsgEl.textContent.trim().length > 0) {
                showModal(successMsgEl.textContent.trim());
            }

            if (modalOkBtn) {
                modalOkBtn.addEventListener('click', function() {
                    hideModal();
                    if (form) {
                        form.reset();
                    }
                });
            }
        })();
    </script>
</body>
</html>












