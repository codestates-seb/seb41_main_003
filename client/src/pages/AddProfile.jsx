import ChangeProfileCard from '../components/ChangeProfile/ChangeProfileCard';
import ChangeProfileContents from '../components/ChangeProfile/ChangeProfileContents';
import styles from './ChangeProfile.module.css';
import { useState } from 'react';
import { ConfirmModal } from '../components/Modal';

const initialState = {
  profile_id: 0,
  user_id: 0,
  name: '',
  rate: 0,
  bio: '',
  want_date: '',
  pay: '',
  way: '',
  profile_status: 'TUTOR',
  wanted_status: null,
  gender: '',
  pre_tutoring: '',
  difference: '',
  school: '',
  character: '',
  subjects: [],
};

const AddProfile = () => {
  const [user, setUser] = useState(initialState);
  const [confirm, setConfirm] = useState(false);
  const confirmText = `입력한 내용으로
  프로필 작성을 완료하시겠습니까?`;

  const confirmHandler = (e) => {
    if (e.target.name === 'yes') {
      console.log('추가 요청'); // TODO : POST 요청
      setConfirm(!confirm); // TODO : 리다이렉트 함수
    } else {
      setConfirm(!confirm);
    }
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <ChangeProfileCard
          user={user}
          setUser={setUser}
          isNew={true}
          setConfirm={setConfirm}
        />
        <ChangeProfileContents user={user} setUser={setUser} />
      </div>
      {confirm ? (
        <ConfirmModal text={confirmText} modalHandler={confirmHandler} />
      ) : undefined}
    </div>
  );
};

export default AddProfile;
