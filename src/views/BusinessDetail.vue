<template>
<div class="page-container">
  <div class="business-detail">
    <div class="cover-image-container">
      <div class="cover-overlay"></div>
      <img :src="coverImage" :alt="business.merchantName">
      <h1 class="business-title">{{ business.merchantName }}</h1>
    </div>

    <div class="content-wrapper">
      <div class="info-section">
        <div class="rating-box">
          <span class="stars">{{ renderStars(business.rating) }}</span>
          <span class="rating-value">{{ business.rating }}分</span>
        </div>
        
        <div class="info-grid">
          <div class="info-item">
            <i class="fas fa-yen-sign"></i>
            <div class="info-text">
              <label>人均消费</label>
              <span>￥{{ business.avgPrice }}</span>
            </div>
          </div>
          
          <div class="info-item">
            <i class="fas fa-map-marker-alt"></i>
            <div class="info-text">
              <label>地址</label>
              <span>{{ business.address }}</span>
            </div>
          </div>
          
          <div class="info-item">
            <i class="fas fa-clock"></i>
            <div class="info-text">
              <label>营业时间</label>
              <span>{{ business.businessHours }}</span>
            </div>
          </div>
          
          <div class="info-item">
            <i class="fas fa-phone"></i>
            <div class="info-text">
              <label>联系电话</label>
              <span>{{ business.telephone }}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="description-section">
        <h3><i class="fas fa-store"></i> 商家特色</h3>
        <p>{{ business.description }}</p>
      </div>

      <div class="gallery-section">
        <h3><i class="fas fa-images"></i> 店铺实景</h3>
        <div class="gallery">
          <div v-for="(photo, index) in processedPhotos" 
               :key="index" 
               class="gallery-item"
               @click="showFullImage(photo)">
            <img :src="photo" alt="商家图片">
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      business: {
        merchantName: '',
        rating: 0,
        avgPrice: 0,
        address: '',
        businessHours: '',
        telephone: '',
        description: '',
        coverUrl: '',
        photoUrls: [],
      }
    }
  },
  computed: {
    // 处理封面图路径
    coverImage() {
      return `http://localhost:8080${this.business.coverUrl}`
    },
    // 处理详情图路径
    processedPhotos() {
      if (!this.business.photoUrls || this.business.photoUrls.length === 0) return [];

      const rawUrls = Array.isArray(this.business.photoUrls)
          ? this.business.photoUrls
          : this.business.photoUrls.split(',');

      return rawUrls.map(url => {
        const trimmedUrl = url.trim();
        return trimmedUrl.startsWith('http')
            ? trimmedUrl
            : `http://localhost:8080${trimmedUrl}`;
      });
    }
  },
  async mounted() {
    try {
      const id = this.$route.params.id;
      const response = await axios.get(`/api/businesses/${id}`);
      this.business = response.data;
    } catch (error) {
      console.error('加载商家详情失败:', error);
    }
  },
  methods: {
    renderStars(rating) {
      const fullStars = Math.floor(rating);
      const halfStar = rating % 1 >= 0.5 ? 1 : 0;
      return '★'.repeat(fullStars) + (halfStar ? '☆' : '');
    },
    showFullImage(photo) {
      // Logic to show full image in a modal or new view
      console.log('Show full image:', photo);
    }
  }
}
</script>

<style scoped>
.page-container {
  min-height: 100vh;
  background: #f5f7fa;
}

.business-detail {
  max-width: 1200px;
  margin: 0 auto;
}

.cover-image-container {
  position: relative;
  height: 400px;
  overflow: hidden;
  border-radius: 0 0 30px 30px;
}

.cover-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 50%;
  background: linear-gradient(to bottom, transparent, rgba(0,0,0,0.7));
}

.cover-image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.business-title {
  position: absolute;
  bottom: 30px;
  left: 40px;
  color: white;
  font-size: 2.5rem;  /* 增大标题文字 */
  font-weight: 600;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
}

.content-wrapper {
  padding: 30px;
  margin-top: -50px;
  position: relative;
}

.info-section {
  background: white;
  border-radius: 15px;
  padding: 25px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
  font-size: 1.1rem;  /* 增大信息文字 */
}

.rating-box {
  text-align: center;
  margin-bottom: 20px;
  font-size: 1.8rem;  /* 增大评分文字 */
}

.stars {
  color: #ffd700;
  font-size: 24px;
  margin-right: 10px;
}

.rating-value {
  font-size: 20px;
  font-weight: bold;
  color: #333;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.info-item {
  display: flex;
  align-items: center;
  padding: 18px;  /* 增加内边距 */
  background: #f8f9fa;
  border-radius: 10px;
}

.info-item i {
  font-size: 1.4rem;  /* 增大图标 */
  margin-right: 15px;
  color: #4a90e2;
}

.info-text {
  display: flex;
  flex-direction: column;
}

.info-text label {
  font-size: 12px;
  color: #666;
  margin-bottom: 4px;
}

.info-text span {
  font-size: 16px;
  color: #333;
}

.description-section, .gallery-section {
  background: white;
  border-radius: 15px;
  padding: 25px;
  margin-top: 20px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.description-section h3 {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #333;
  margin-bottom: 15px;
  font-size: 1.6rem;  /* 增大描述标题 */
}

.description-section p {
  line-height: 1.8;  /* 增加行高 */
  font-size: 1.1rem;  /* 增大描述文字 */
  color: #666;
}

.gallery {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 15px;
  margin-top: 20px;
}

.gallery-item {
  aspect-ratio: 1;
  overflow: hidden;
  border-radius: 10px;
  cursor: pointer;
  transition: transform 0.3s;
}

.gallery-item:hover {
  transform: scale(1.05);
}

.gallery-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.gallery-section h3 {
  font-size: 1.6rem;  /* 增大相册标题 */
}
</style>