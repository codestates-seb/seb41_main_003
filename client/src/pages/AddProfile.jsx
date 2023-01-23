import ChangeProfileCard from '../components/ChangeProfile/ChangeProfileCard';
import ChangeProfileContents from '../components/ChangeProfile/ChangeProfileContents';
import styles from './ChangeProfile.module.css';
import { useState, useEffect } from 'react';
import { ButtonTop } from '../components/Button';
import ModalState from '../recoil/modal.js';
import { useSetRecoilState, useResetRecoilState } from 'recoil';

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

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const confirmHandler = (e) => {
    const { name } = e.target;
    if (name === 'yes') setUser(JSON.parse(localStorage.addProfile));
    localStorage.removeItem('addProfile');
    resetModal();
  };
  const confirmProps = {
    isOpen: true,
    modalType: 'bothHandler',
    backDropHandle: true,
    props: {
      text: `작성 중인 프로필이 있습니다. 계속 작성하시겠습니까?
      취소를 누르시면 임시 저장된 데이터가 사라집니다.`,
      modalHandler: confirmHandler,
    },
  };

  //* 렌더링 될 때 로컬 스토리지에 키가 있는지 파악하여 모달 출력
  useEffect(() => {
    if (localStorage.addProfile) setModal(confirmProps);
  }, []);

  useEffect(() => {
    //* 초기 상태 값에서 변화가 있으면 로컬 스토리지에만 저장하도록 변경
    if (JSON.stringify(user) !== JSON.stringify(initialState))
      localStorage.setItem('addProfile', JSON.stringify(user));
  }, [user]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <ChangeProfileCard user={user} setUser={setUser} isNew={true} />
        <ChangeProfileContents user={user} setUser={setUser} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default AddProfile;
