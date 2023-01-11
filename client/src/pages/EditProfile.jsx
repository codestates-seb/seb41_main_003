import ChangeProfileCard from '../components/ChangeProfile/ChangeProfileCard';
import ChangeProfileContents from '../components/ChangeProfile/ChangeProfileContents';
import styles from './ChangeProfile.module.css';
import { useState } from 'react';
import { ConfirmModal } from '../components/Modal';

const tutorDummyState = {
  profile_id: 1,
  user_id: 1,
  name: '신승구',
  rate: 4.4,
  bio: '하고싶은 대로 가르칩니다',
  want_date: '아무때나',
  pay: '많이 주면 감사',
  way: '알잘딱하게 합니다',
  profile_status: 'TUTOR',
  wanted_status: true,
  gender: '남자',
  pre_tutoring: '안할래요',
  difference: '뭔가 달라도 다르다니까요',
  school: '서울사이버대학교 성공시대학과 77학번',
  character: '건들면 뭅니다(진짜임)',
  subjects: [
    { subjectId: 1, subjectTitle: '영어', content: '영어 잘합니다' },
    { subjectId: 2, subjectTitle: '수학', content: '수학 잘합니다' },
  ],
};

const tuteeDummyState = {
  profile_id: 1,
  user_id: 1,
  name: '신승구',
  rate: 4.5,
  bio: '내 알아 할게요',
  want_date: '아무때나',
  pay: '싸게 해주면 감사',
  way: '알잘딱한 선생을 원합니다',
  profile_status: 'TUTEE',
  wanted_status: true,
  gender: '남자',
  pre_tutoring: '해줘',
  difference: '',
  school: '고등학교 3학년',
  character: '물리는걸 참지 않습니다',
  subjects: [
    { subjectId: 1, subjectTitle: '영어' },
    { subjectId: 2, subjectTitle: '수학' },
  ],
};

const EditProfile = () => {
  const [user, setUser] = useState(tutorDummyState);
  const [isConfirm, setIsConfirm] = useState(false);
  const confirmText = `현재 입력된 내용으로
  프로필을 수정하시겠습니까?`;

  const confirmHandler = (e) => {
    if (e.target.name === 'yes') {
      console.log('수정 요청'); // TODO : PATCH 요청
      setIsConfirm(!isConfirm); // TODO : 리다이렉트 함수
    } else {
      setIsConfirm(!isConfirm);
    }
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <ChangeProfileCard
          user={user}
          setUser={setUser}
          isNew={false}
          setIsConfirm={setIsConfirm}
        />
        <ChangeProfileContents user={user} setUser={setUser} />
      </div>
      {isConfirm && (
        <ConfirmModal text={confirmText} modalHandler={confirmHandler} />
      )}
    </div>
  );
};

export default EditProfile;
