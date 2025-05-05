<template>
  <div class="container">
    <!-- 用户信息展示 -->
    <div class="user-info" @click="goToUserInfo">
      <span class="username">{{ userInfo.username }}</span>
      <div class="avatar">
        {{ userInfo.username?.charAt(0)?.toUpperCase() }}
      </div>
    </div>

    <!-- 主内容区域 -->
    <h1 class="welcome-title">欢迎来到生活服务平台</h1>

    <div class="button-group">
      <button
          class="main-btn food-btn"
          @click="$router.push('/nearby-food')"
      >
        <i class="fas fa-utensils"></i>
        附近美食
      </button>

      <button
          class="main-btn review-btn"
          @click="$router.push('/add-review')"
      >
        <i class="fas fa-edit"></i>
        我要评价
      </button>

      <button
          class="main-btn life-btn"
          @click="$router.push('/life-zone')"
      >
        <i class="fas fa-shopping-basket"></i>
        生活专区
      </button>
    </div>

    <button class="logout-btn" @click="handleLogout">
      <i class="fas fa-sign-out-alt"></i>
      退出登录
    </button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInfo: {
        id: '',
        username: '未登录用户'
      }
    }
  },
  mounted() {
    const userData = localStorage.getItem('userInfo')
    if (userData) {
      this.userInfo = JSON.parse(userData)
    } else {
      this.$router.push('/login')
    }
  },
  methods: {
    handleLogout() {
      localStorage.removeItem('userInfo')
      this.$router.push('/login')
    },
    goToUserInfo() {
      this.$router.push('/user-info')
    }
  }
}
</script>

<style scoped>
.container {
  padding: 2rem;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.user-info {
  position: fixed;
  top: 20px;
  right: 30px;
  display: flex;
  align-items: center;
  gap: 12px;
  cursor: pointer;
  transition: all 0.3s;
}

.user-info:hover {
  transform: scale(1.05);
}

.avatar {
  width: 40px;
  height: 40px;
  background: #4a90e2;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: bold;
}

.username {
  font-size: 1.1rem;
  color: #333;
  font-weight: 500;
}

.welcome-title {
  text-align: center;
  margin: 80px 0 40px;
  color: #2c3e50;
  font-size: 2.2rem;
}

.button-group {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.main-btn {
  width: 100%;
  height: 180px;
  border: none;
  border-radius: 20px;
  font-size: 1.5rem;
  color: white;
  cursor: pointer;
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 15px;
  box-shadow: 0 10px 20px rgba(0,0,0,0.1);
}

.main-btn:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 30px rgba(0,0,0,0.2);
}

.food-btn { background: linear-gradient(45deg, #ff6b6b, #ff8e8e); }
.review-btn { background: linear-gradient(45deg, #4ecdc4, #66d8d1); }
.life-btn { background: linear-gradient(45deg, #ff9a44, #ffb347); }

.logout-btn {
  position: fixed;
  bottom: 30px;
  right: 30px;
  padding: 12px 25px;
  background: #e74c3c;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: all 0.3s;
}

.logout-btn:hover {
  background: #c0392b;
  transform: scale(1.05);
}

.fas {
  font-size: 2.5rem;
}
</style>
