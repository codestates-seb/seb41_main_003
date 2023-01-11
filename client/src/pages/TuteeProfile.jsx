import styles from './TuteeProfile.module.css';
import { ProfileContents, ProfileCard } from '../components/profileSection';

const TuteeProfile = () => {
  const tutorDummyState = {
    profile_id: 1,
    user_id: 1,
    name: '신승구',
    rate: 4.4,
    bio: '하고싶은 대로 가르칩니다',
    want_date: '아무때나',
    pay: '많이 주면 감사',
    way: '알잘딱하게 합니다',
    profile_status: 'TUTEE',
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
          '영어 잘합니다영어 잘합니다영어 잘합니다영어 잘합니 잘합니다영어 잘합니다영어 잘 잘합니다영어 잘합니다영어 잘 잘합니다영어 잘합니다영어 잘 잘합니다영어 잘합니다영어 잘 잘합니다영어 잘합니다영어 잘다',
      },
      { subjectId: 2, subjectTitle: '수학', content: '수학 잘합니다' },
    ],
  };
  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <div className={styles.container}>
          <ProfileCard user={tutorDummyState} />
          <ProfileContents user={tutorDummyState} />
        </div>
      </div>
    </div>
  );
};

export default TuteeProfile;
