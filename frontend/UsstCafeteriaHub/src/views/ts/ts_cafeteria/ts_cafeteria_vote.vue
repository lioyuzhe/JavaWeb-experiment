<template>
  <div class="votes-container">
    <el-row :gutter="20">
      <el-col :span="16" :offset="4">
        <el-card
            class="vote-card"
            shadow="hover"
            v-for="(vote, index) in votes"
            :key="index"
        >
          <h2 class="vote-title">{{ vote.voteTitle }}</h2>
          <p class="vote-description">{{ vote.voteDescription }}</p>
          <el-radio-group v-model="answers[index]">
            <el-radio
                v-for="option in vote.voteOptions"
                :label="option.OptionID"
                :key="option.OptionID"
            >{{ option.OptionDescription }}</el-radio>
          </el-radio-group>
          <div class="vote-action">
            <el-button type="primary" @click="submitVote(index)">提交投票</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  props: {
    cafeteria: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      votes: [
        {
          voteTitle: '您最喜欢的主食是哪一种？',
          voteDescription: '请选择您最常吃的一种主食。',
          selectedOption: null,
          voteOptions: [
            { OptionID: 'main_1', OptionDescription: '米饭' },
            { OptionID: 'main_2', OptionDescription: '面条' },
            { OptionID: 'main_3', OptionDescription: '馒头' },
            { OptionID: 'main_4', OptionDescription: '炒饭' },
          ],
        },
        {
          voteTitle: '您午餐喜欢吃哪种风味的菜品？',
          voteDescription: '请选择您午餐时候偏好的风味。',
          selectedOption: null,
          voteOptions: [
            { OptionID: 'flavor_1', OptionDescription: '辣' },
            { OptionID: 'flavor_2', OptionDescription: '酸甜' },
            { OptionID: 'flavor_3', OptionDescription: '家常' },
            { OptionID: 'flavor_4', OptionDescription: '清淡' },
          ],
        },
        {
          voteTitle: '您晚餐最喜欢的菜品是什么？',
          voteDescription: '请选择您晚餐时喜欢的菜品。',
          selectedOption: null,
          voteOptions: [
            { OptionID: 'dinner_1', OptionDescription: '红烧肉' },
            { OptionID: 'dinner_2', OptionDescription: '清蒸鱼' },
            { OptionID: 'dinner_3', OptionDescription: '宫保鸡丁' },
            { OptionID: 'dinner_4', OptionDescription: '麻婆豆腐' },
          ],
        },
        // 根据需要继续添加更多投票问题...
      ],
      answers: {},
    };
  },
  methods: {
    submitVote(voteIndex) {
      const voteID = this.votes[voteIndex].voteTitle; // 假设使用投票标题作为ID
      const selectedOption = this.answers[voteIndex];
      console.log(`投票结果 - ${voteID}:`, selectedOption);
      // 在这里添加将投票结果提交到服务器的代码
    },
    goToCafeteriaVote(entry) {
      if (entry.id === 'vote') {
        this.$router.push({ name: 'CafeteriaVote' }); // 使用路由的name进行跳转
      }
    },
  },


};
</script>

<style scoped>
.vote-section {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.vote-card {
  width: 100%;
  max-width: 600px; /* 或者根据您的具体需求调整宽度 */
}

.vote-title {
  text-align: center;
  margin-bottom: 10px;
}

.vote-description {
  text-align: justify;
  margin-bottom: 20px;
}

.vote-options {
  margin-bottom: 20px;
}

.vote-action {
  text-align: right;
}
</style>
