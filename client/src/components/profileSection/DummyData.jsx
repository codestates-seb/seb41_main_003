const DummyData = {
  profile_id: 1,
  user_id: 1,
  name: '신승구',
  rate: 4.4,
  bio: '하고싶은 대로 가르칩니다',
  want_date: '아무때나',
  pay: '많이 주면 감사',
  way: '알잘딱하게 합니다',
  profile_status: 'TUTOR',
  wanted_status: 'REQUEST',
  gender: '남자',
  pre_tutoring: '안할래요',
  difference: '뭔가 달라도 다르다니까요',
  school: '서울사이버대학교 성공시대학과 77학번',
  character: '건들면 뭅니다(진짜임)',
  subjects: [
    {
      subjectId: 1,
      subjectTitle: '영어',
      content:
        'All you need in this life is ignorance and confidence; then success is sure.',
    },
    { subjectId: 2, subjectTitle: '수학', content: '수학 잘합니다' },
  ],
  reviews: [
    {
      reviewId: 1,
      professional: 4,
      readiness: 4,
      explanation: 5,
      punctuality: 5,
      reviewBody: 'TestBody',
      tuteeName: '로이',
      createAt: '2023-01-11T04:11:43.078373',
      updateAt: '2023-01-11T04:11:43.078379',
    },
    {
      reviewId: 2,
      professional: 4,
      readiness: 4,
      explanation: 5,
      punctuality: 5,
      reviewBody: 'TestBodyTestBodyTestBody',
      tuteeName: '로사',
      createAt: '2023-01-11T04:11:43.078373',
      updateAt: '2023-01-11T04:11:43.078379',
    },
    {
      reviewId: 3,
      professional: 4,
      readiness: 4,
      explanation: 5,
      punctuality: 5,
      reviewBody: 'TestBodyTestBody',
      tuteeName: '냐옹이',
      createAt: '2023-01-11T04:11:43.078373',
      updateAt: '2023-01-11T04:11:43.078379',
    },
  ],
};
export default DummyData;