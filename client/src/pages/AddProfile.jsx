import ChangeProfileCard from '../components/ChangeProfile/ChangeProfileCard';
import ChangeProfileContents from '../components/ChangeProfile/ChangeProfileContents';
import styles from './ChangeProfile.module.css';
import { useState } from 'react';
import { ConfirmModal, AlertModal } from '../components/Modal';
import { ButtonTop } from '../components/Button';

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
  const [isConfirm, setIsConfirm] = useState(false);
  const [isRequired, setIsRequired] = useState(false);
  const confirmText = `입력한 내용으로
  프로필 작성을 완료하시겠습니까?`;

  const confirmHandler = (e) => {
    e.preventDefault();
    if (e.target.name === 'yes') {
      console.log('추가 요청'); // TODO : POST 요청
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
          isNew={true}
          confirmHandler={confirmHandler}
          setIsConfirm={setIsConfirm}
          setIsRequired={setIsRequired}
        />
        <ChangeProfileContents user={user} setUser={setUser} />
      </div>
      {isConfirm && (
        <ConfirmModal text={confirmText} modalHandler={confirmHandler} />
      )}
      <ButtonTop />
      {isRequired && (
        <AlertModal
          text="필수 입력 사항을 모두 작성해주세요."
          modalHandler={() => setIsRequired(!isRequired)}
        />
      )}
    </div>
  );
};

export default AddProfile;
