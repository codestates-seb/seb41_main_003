import ChangeProfileCard from '../components/ChangeProfile/ChangeProfileCard';
import ChangeProfileContents from '../components/ChangeProfile/ChangeProfileContents';
import styles from './ChangeProfile.module.css';
import { useState, useEffect } from 'react';
import { ConfirmModal, AlertModal } from '../components/Modal';
import { ButtonTop } from '../components/Button';

const initialState = {
  name: '',
  bio: '',
  wantDate: '',
  pay: '',
  way: '',
  profileStatus: 'TUTOR',
  gender: '',
  preTutoring: '',
  difference: '',
  school: '',
  character: '',
  subjects: [],
};

const AddProfile = () => {
  const [user, setUser] = useState(initialState);
  const [isConfirm, setIsConfirm] = useState(false);
  const [isRequired, setIsRequired] = useState(false);
  const [isDraft, setIsDraft] = useState(false);

  const confirmSubmitText = `입력한 내용으로
  프로필 작성을 완료하시겠습니까?`;

  const confirmDraftText = `작성 중인 프로필이 있습니다. 계속 작성하시겠습니까?
  취소를 누르시면 임시 저장된 데이터가 사라집니다.`;

  //? 렌더링 될 때 로컬 스토리지에 키가 있는지 파악함
  //? 있다면 모달 띄워 줌
  useEffect(() => {
    if (localStorage.addProfile) setIsDraft(true);
  }, []);

  //? user 상태가 변화하면 자동으로 저장함
  //? 하지만 이 Effect Hook 역시 렌더가 될 때 자동으로 실행되므로 자동으로 initialState를 저장함

  useEffect(() => {
    //? 초기 상태 값에서 변화가 있으면 로컬 스토리지에만 저장하도록 변경
    if (JSON.stringify(user) !== JSON.stringify(initialState))
      localStorage.setItem('addProfile', JSON.stringify(user));
  }, [user]);
  /**
   * 자동 저장 관련 모달 핸들러
   */
  const draftHandler = (e) => {
    const { name } = e.target;
    if (name === 'yes') {
      setUser(JSON.parse(localStorage.addProfile));
    }
    localStorage.removeItem('addProfile');
    setIsDraft(false);
  };

  /**
   * 추가 요청 관련 모달 핸들러
   */
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
        <ConfirmModal text={confirmSubmitText} modalHandler={confirmHandler} />
      )}
      <ButtonTop />
      {isDraft && (
        <ConfirmModal text={confirmDraftText} modalHandler={draftHandler} />
      )}
    </div>
  );
};

export default AddProfile;
