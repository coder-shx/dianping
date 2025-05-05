
<style scoped>
.auth-page {
  display: flex;
  height: 100vh;
}

.background-section {
  flex: 1;
  position: relative;
  overflow: hidden;
}

.background-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.background-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.3);
}

.form-section {
  width: 400px;
  background: rgba(255, 255, 255, 0.95);
  padding: 40px;
  display: flex;
  flex-direction: column;
}

.auth-switch {
  display: flex;
  margin-bottom: 40px;
}

.auth-switch h2 {
  flex: 1;
  text-align: center;
  color: #999;
  cursor: pointer;
  padding-bottom: 10px;
  border-bottom: 2px solid transparent;
}

.auth-switch h2.active {
  color: #ff4444;
  border-color: #ff4444;
}

.form-item {
  position: relative;
  margin-bottom: 25px;
}

.form-item input {
  width: 100%;
  height: 50px;
  padding: 10px 0 10px 40px; /* 左右留出图标空间 */
  border: 4px solid #ddd;
  border-radius: 2px;
}

/* 输入框聚焦效果 */
input:focus {
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
  background-color: #fff;
}

.submit-btn {
  width: 100%;
  height: 50px;
  background: #ff4444;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.agreement {
  margin-top: 15px;
  font-size: 12px;
  color: #666;
}

.agreement a {
  color: #ff4444;
}

.footer-links {
  margin-top: auto;
  display: flex;
  justify-content: center;
  gap: 20px;
  padding-top: 30px;
}

.footer-links a {
  color: #666;
  font-size: 12px;
}

.password-strength {
  display: flex;
  align-items: center;
  margin-top: 5px;
}

.toggle-password {
  position: absolute;
  right: 10px;
  top: 35px;
  transform: translateY(-50%);
  cursor: pointer;
  color: #95a5a6;
}

/* 调整强度条样式 */
.strength-bar {
  width: 60px;
  height: 8px;
  margin-right: 0;
}

.weak {
  background: #ff4444;
}

.medium {
  background: #ffbb33;
}

.strong {
  background: #00C851;
}

.very-strong {
  background: #00C851;
}

.captcha-item {
  display: flex;
  gap: 10px;
}

.captcha-img {
  height: 45px;
  cursor: pointer;
  border-radius: 4px;
}

.social-icons i {
  font-size: 24px;
  cursor: pointer;
  color: #666;
}

/* 密码切换按钮悬停效果 */
.toggle-password:hover {
  color: #3498db;
}

/* 新增错误提示样式 */
.form-item::after {
  content: attr(data-error);
  position: absolute;
  bottom: -18px;
  left: 0;
  color: #ff4444;
  font-size: 12px;
}

.validUsername-text {
  color: #e74c3c;
  display: block;
  animation: fadeIn 0.3s ease;
}

/* 密码强度指示条容器 */
.password-strength {
  width: 250px;
  height: 4px;
  background: #eee;
  border-radius: 2px;
  margin-top: 8px;
  overflow: hidden;
}

/* 强度文字样式 */
.strength-text {
  display: block;
  font-size: 16px;
  color: #95a5a6;
  margin-top: 8px;
  animation: fadeIn 0.3s ease;
}

/* 动态强度条样式 */
.strength-bar {
  height: 100%;
  width: 100%;
  transition: all 0.3s ease;
}

.weak {
  width: 30%;
  background: #ff4444;
}

.medium {
  width: 60%;
  background: #ffbb33;
}

.strong {
  width: 90%;
  background: #00C851;
}

.very-strong {
  width: 100%;
  background: #00C851;
}

/* 左侧图标通用样式 */
.icon-user,
.icon-lock {
  position: absolute;
  left: 20px;
  top: 36px;
  transform: translateY(-50%);
  color: #95a5a6;
  pointer-events: none; /* 防止图标阻挡输入框点击 */
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-2px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>