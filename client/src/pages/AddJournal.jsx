import styles from './ChangeJournal.module.css';
import { useEffect } from 'react';
import EditJournalForm from '../components/JournalSection/EditJournalForm';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../recoil/modal.js';
import ChangeJournal from '../recoil/journal';
import dayjs from 'dayjs';

const AddJournal = () => {
  const userData = useRecoilValue(ChangeJournal);
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const resetJournal = useResetRecoilState(ChangeJournal);

  const confirmProps = {
    isOpen: true,
    modalType: 'bothHandler',
    props: {
      text: `작성 중인 일지가 있습니다. 계속 작성하시겠습니까?
      취소를 누르시면 임시 저장된 데이터가 사라집니다.`,
      modalHandler: (e) => {
        const { name } = e.target;
        if (name === 'no') {
          resetJournal();
        }
        resetModal();
      },
    },
  };

  //* 렌더링 될 때 로컬 스토리지에 키가 있는지 파악하여 모달 출력
  useEffect(() => {
    const initial = JSON.stringify({
      dateNoticeTitle: '',
      startTime: dayjs()
        .set('hour', 9)
        .set('minute', 0)
        .format('YYYY-MM-DD HH:mm'),
      endTime: dayjs()
        .set('hour', 18)
        .set('minute', 0)
        .format('YYYY-MM-DD HH:mm'),
      scheduleBody: '',
      noticeBody: '',
      homeworks: [],
    });
    if (initial !== JSON.stringify(userData)) setModal(confirmProps);
  }, []);

  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <EditJournalForm />
      </div>
    </div>
  );
};

export default AddJournal;
