<template>
  <div class="cafeteria-intro">
    <h2 class="cafeteria-title">一食堂</h2>
    <p>一食堂，作为上海理工大学最大的食堂，不仅在校园内占据着重要的地理位置，而且以其历史悠久和菜品多样而著称。位于校内东南角，它是学校的主食堂，同时也是最早建成的食堂之一。</p>
    <p>食堂共有三层，每一层都有其独特的风味和特色。一层主要提供各类快餐和小吃，适合忙碌的学生和教职工快速享用；二层是专为教职工设计的餐厅，环境优雅，菜品更加精致；三层则是学生餐厅，不仅空间宽敞，而且提供更加丰富多样的选择。</p>
    <p>在一食堂，您可以尝到从传统中式炒菜到现代快餐，再到各种地方特色小吃的丰富选择。无论是热腾腾的汤品、新鲜的凉菜、多样的主食，还是甜美的水果和饮料，都能满足您的味蕾。且所有菜品都保证价格亲民、味道可口，确保每位来访者都能在这里找到满意的美食。</p>
    <p>一食堂不仅仅是一个用餐场所，它也是校园文化的一个重要组成部分。这里是学生和教职工交流思想、分享故事的场所，充满了活力和温馨的氛围。</p>
    <p>欢迎您来到一食堂，体验这里独有的美食文化，享受舌尖上的美味旅程！</p>
  </div>
</template>

<script>
export default {
  name: 'ts_cafeteria_introduction'
  // 可以添加一些逻辑
}
</script>

<style scoped>
.cafeteria-intro {
  padding: 30px; /* 增加内边距 */
  background-color: #f8f8f8;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  font-family: 'Arial', sans-serif;
}

.cafeteria-title {
  color: #2c3e50;
  text-align: center;
  margin-bottom: 20px;
}

p {
  text-align: justify;
  line-height: 1.6;
  margin-bottom: 10px;
  text-indent: 2em; /* 首行缩进 */
}
</style>
