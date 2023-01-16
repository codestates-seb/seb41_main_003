import styles from './ChangeJournal.module.css';
import { useState, useEffect } from 'react';
import EditJournalForm from '../components/JournalSection/EditJournalForm';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../recoil/modal.js';

const jounalDummy = {
  dateNoticeTitle: '',
  startTime: new Date(new Date().setHours(9, 0)).toISOString(),
  endTime: new Date(new Date().setHours(18, 0)).toISOString(),
  scheduleBody: '',
  noticeBody: '',
  Homeworks: [],
};

const AddJournal = () => {
  const [user, setUser] = useState(jounalDummy);
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const confirmHandler = (e) => {
    const { name } = e.target;
    if (name === 'yes') {
      setUser({ ...JSON.parse(localStorage.addJournal) });
    }
    localStorage.removeItem('addJournal');
    resetModal();
  };
  const confirmProps = {
    isOpen: true,
    modalType: 'bothHandler',
    props: {
      text: `작성 중인 일지가 있습니다. 계속 작성하시겠습니까?
      취소를 누르시면 임시 저장된 데이터가 사라집니다.`,
      modalHandler: confirmHandler,
    },
  };

  //* 렌더링 될 때 로컬 스토리지에 키가 있는지 파악하여 모달 출력
  useEffect(() => {
    if (localStorage.addJournal) setModal(confirmProps);
  }, []);

  useEffect(() => {
    //* 초기 상태 값에서 변화가 있으면 로컬 스토리지에만 저장하도록 변경
    const u = {
      ...user,
      startTime: user.startTime.slice(0, 16),
      endTime: user.endTime.slice(0, 16),
    };
    const j = {
      ...jounalDummy,
      startTime: jounalDummy.startTime.slice(0, 16),
      endTime: user.endTime.slice(0, 16),
    };
    if (JSON.stringify(u) !== JSON.stringify(j)) {
      localStorage.setItem('addJournal', JSON.stringify(user));
    }
  }, [user]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <EditJournalForm user={user} setUser={setUser} />
      </div>
    </div>
  );
};

export default AddJournal;
