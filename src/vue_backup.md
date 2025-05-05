<!-- AuthPage.vue -->
<template>
  <div class="auth-page">
    <!-- 左侧背景图 -->
    <div class="background-section">
      <div class="background-overlay"></div>
      <img src="@/assets/background.jpg" alt="背景图" class="background-image">
    </div>

    <!-- 右侧表单区域 -->
    <div class="form-section">
      <!-- 登录/注册切换 -->
      <div class="auth-switch">
        <h2 :class="{ active: !isLogin }" @click="switchForm('register')">注册</h2>
        <h2 :class="{ active: isLogin }" @click="switchForm('login')">登录</h2>
      </div>

      <!-- 注册表单 -->
      <form v-if="!isLogin" @submit.prevent="handleRegister" class="auth-form">
        <div class="form-item">
          <input
              type="text"
              v-model="registerForm.username"
              placeholder="用户名"
              @input="validUsername"
              @blur="validUsername"
          ><!-- 直接使用 Font Awesome 图标 -->
          <span class="icon-user"><i class="fas fa-user"></i></span>

          <div class="username-wrapper">
             <span class="validUsername-text">{{ userNameMessage }}</span>
            <span class="error-message" :class="{shake: showError}">{{ userNameMessage }}</span>
          </div>
        </div>
        <div class="form-item">
          <input
              :type="showPassword ? 'text' : 'password'"
              v-model="registerForm.password"
              placeholder="密码"
              @input="checkPasswordStrength"
              inputmode="latin"
              @compositionstart="disableIME"
              @compositionend="disableIME"
          ><!-- 使用 Font Awesome 的锁图标 -->
          <span class="icon-lock"><i class="fas fa-lock"></i></span>
          <!-- 添加密码显示切换按钮 -->
          <span class="toggle-password" @click="togglePassword">
<i :class="showPassword ? 'fa fa-eye' : 'fa fa-eye-slash'"></i>
</span>
<!-- 密码强度显示 -->
<div class="password-strength-wrapper">
<div class="password-strength">
<div class="strength-bar" :class="strengthClass" :style="{ width: strengthWidth }"></div>
</div>
<span class="strength-text">{{ passwordStrength }}</span>
<span class="error-message" :class="{shake: showError}">{{ passwordError }}</span>
</div>

        </div>

        <div class="form-item captcha-item">
          <input
              v-model="registerForm.captcha"
              placeholder="验证码"
          >
          <img
              :src="captchaUrl"
              class="captcha-img"
              @click="refreshCaptcha"
          >
        </div>

        <button type="submit" class="submit-btn">立即注册</button>

        <!-- 用户协议 -->
        <div class="agreement">
          点击注册即表示同意
          <a href="/agreement">《用户协议》</a>和
          <a href="/privacy">《隐私政策》</a>
        </div>
      </form>

      <!-- 登录表单 -->
      <form v-else="isLogin" @submit.prevent="handleLogin" class="auth-form">
        <div class="form-item">
          <input
              type="text"
              v-model="loginForm.username"
              placeholder="用户名"
              @blur="validLoginUsername"
          >
          <!-- 直接使用 Font Awesome 图标 -->
          <span class="icon-user"><i class="fas fa-user"></i></span>
        </div>

        <div class="form-item">
          <input
              :type="showPassword ? 'text' : 'password'"
              v-model="loginForm.password"
              placeholder="密码"
              @input="checkPasswordStrength"
              inputmode="latin"
              @compositionstart="disableIME"
              @compositionend="disableIME"
          >
          <!-- 使用 Font Awesome 的锁图标 -->
          <span class="icon-lock"><i class="fas fa-lock"></i></span>
          <!-- 添加密码显示切换按钮 -->
          <span class="toggle-password" @click="togglePassword">
<i :class="showPassword ? 'fa fa-eye' : 'fa fa-eye-slash'"></i>
</span>
</div>

        <button type="submit" class="submit-btn">立即登录</button>
        <!-- 第三方登录？ -->
      </form>


      <!-- 底部链接 -->
      <div class="footer-links">
        <a href="/about">关于我们</a>
        <a href="/contact">联系我们</a>
        <a href="/help">用户帮助</a>
        <a href="/partner">合作伙伴</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLogin: true,
      captchaUrl: '/api/captcha?' + Date.now(),
      passwordError: '',
      captchaError: '',
      isCheckingUsername: false,
      isUsernameAvailable: false,
      passwordStrengthRules: [
        {regex: /.{8,}/, score: 1},       // 长度>=8
        {regex: /.{10,}/, score: 1},      //长度>=10
        {regex: /.{15,}/, score: 1},      //长度>=15
        {regex: /[A-Z]/, score: 1},       // 包含大写字母
        {regex: /[0-9]/, score: 1},       // 包含数字
        {regex: /[^A-Za-z0-9]/, score: 3} // 包含特殊字符
      ],
      // 显示密码状态
      showPassword: false,
      // 修改后的强度等级配置
      strengthLevels: [
        {class: 'none', text: '密码不可用', width: '0'},
        {class: 'weak', text: '密码强度：弱', width: '20%'},
        {class: 'medium', text: '密码强度：中', width: '50%'},
        {class: 'strong', text: '密码强度：强', width: '80%'},
        {class: 'very-strong', text: '密码强度：非常强', width: '100%'}
      ],
      loginForm: {
        username: '',
        password: ''
      },
      registerForm: {
        phone: '',
        password: '',
        captcha: ''
      },
      passwordStrength: '密码不可用',
      strengthClass: 'none', strengthWidth: '0',
      userNameMessage: '',
    }
  },
  methods: {
    switchForm(type) {
      this.isLogin = type === 'login'
    },
    // 用户名校验方法
    async validUsername() {
      const username = this.registerForm.username

      // 格式校验
      if (!/^[a-zA-Z0-9_]{4,20}$/.test(username)) {
        this.userNameMessage = '用户名需为4-20位字母、数字或下划线'
        return
      }this.userNameMessage = '';

      // // 检查用户名是否存在
      // try {
      //   this.isCheckingUsername = true
      //   const res = await this.$http.get(`/user/check?username=${username}`)
      //   this.isUsernameAvailable = !res.data.exists
      //   if (!this.isUsernameAvailable) {
      //     this.userNameMessage = '该用户名已被注册，暂不可用'
      //   }
      //   this.userNameMessage = '可用＜（＾－＾）＞'
      // } catch (err) {
      //   this.$message.error('用户名检查失败')
      // } finally {
      //   this.isCheckingUsername = false
      // }
    },
    togglePassword() {
      this.showPassword = !this.showPassword;
    },

    async handleLogin() {
      if (!this.validLoginUsername() || !this.validLoginPassword()) return
      try {
        const res = await this.$http.post('/login', this.loginForm)
        localStorage.setItem('token', res.data.token)
        this.$router.push('/home') // 跳转到首页
      } catch (err) {
        this.$message.error(err.response?.data?.message || '登录失败')
      }
    },

    // 密码强度计算
    checkPasswordStrength() {
      const password = this.registerForm.password
      this.passwordError = ''

      // 基础校验
      if (password.length < 6) {
        this.passwordStrength = '密码不可用'
        this.strengthClass = 'none'
        this.strengthWidth = '0'
        this.passwordError = '密码长度不能小于6位'
        return
      }
      if (!/\d/.test(password) || !/[a-zA-Z]/.test(password)) {
        this.passwordStrength = '密码不可用'
        this.strengthClass = 'none'
        this.strengthWidth = '0'
        this.passwordError = '密码必须同时包含字母和数字'
        return
      }
      this.passwordError=''
      // 强度计算
      let score = 0
      this.passwordStrengthRules.forEach(rule => {
        if (rule.regex.test(password)) score += rule.score
      })
      console.log(score);
      // 根据分数确定强度等级
      let levelIndex = 0
      if (score >= 7) levelIndex = 4      // 非常强
      else if (score >= 5) levelIndex = 3  // 强
      else if (score >= 3) levelIndex = 2  // 中
      else levelIndex = 1

      this.strengthClass = this.strengthLevels[levelIndex].class
      this.passwordStrength = this.strengthLevels[levelIndex].text
      this.strengthWidth = this.strengthLevels[levelIndex].width
    },
    // 注册处理
    async handleRegister() {
      // 前置校验
      if (!this.isUsernameAvailable) {
        return this.$message.error('请先验证用户名有效性')
      }
      if (this.passwordError) {
        return this.$message.error(this.passwordError)
      }
      if (!this.registerForm.captcha) {
        return this.$message.error('请输入验证码')
      }
      //
      // try {
      //   const res = await this.$http.post('/register', {
      //     username: this.registerForm.username,
      //     password: this.registerForm.password,
      //     captcha: this.registerForm.captcha
      //   })
      //
      //   // 注册成功后自动登录
      //   localStorage.setItem('token', res.data.token)
      //   this.$router.push('/home')
      // } catch (err) {
      //   const msg = err.response?.data?.message || '注册失败'
      //   if (msg.includes('验证码')) {
      //     this.captchaError = msg
      //     this.refreshCaptcha()
      //   }
      //   this.$message.error(msg)
      // }
    },
    disableIME(event) {
      event.target.blur(); // 失去焦点，强制用户重新点击输入框
      setTimeout(() => event.target.focus(), 0); // 重新聚焦
    },
    // 完善登录校验
    validLoginUsername() {
      if (!this.loginForm.username) {
        this.$message.error('请输入用户名')
        return false
      }
      return true
    },
    validLoginPassword() {
      if (!this.loginForm.password) {
        this.$message.error('请输入密码')
        return false
      }
      return true
    },
    refreshCaptcha() {
      this.captchaUrl = `/api/captcha?t=${Date.now()}`
    },

  }
}
</script>
<!-- 在.vue文件的style标签中引入 -->
<style scoped>
@import url('@/assets/css/form-style.css');
@import url('@/assets/css/error-style.css');

/* 组件特有样式可以追加在这里 */
</style>