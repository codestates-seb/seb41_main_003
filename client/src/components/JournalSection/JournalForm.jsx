import styles from './JournalForm.module.css';
import PropType from 'prop-types';
import { ButtonSilver } from '../Button';
import { CheckBox } from '../Input';
import DropDown from './DropDown';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';
import { useNavigate, useLocation } from 'react-router-dom';
import dayjs from 'dayjs';

const JournalForm = ({ userData }) => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const { tutoringId, isFinished } = useLocation().state;
  const userStatus = sessionStorage.getItem('userStatus');

  const Navigate = useNavigate();
  const {
    dateNoticeTitle,
    startTime,
    endTime,
    scheduleBody,
    noticeBody,
    homeworks,
  } = userData;

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '과외 리스트 페이지로 이동하시겠습니까?',
      modalHandler: () => {
        Navigate(`/tutoring`, {
          state: { tutoringId },
        });
        resetModal();
      },
    },
  };

  return (
    <div className={styles.container}>
      <h1>과외 일지 </h1>
      <div className={styles.journalContainer}>
        <div className={styles.exitButton}>
          <ButtonSilver text="나가기" buttonHandler={() => setModal(confirm)} />
        </div>
        <section className={styles.upperPart}>
          <div className={styles.pickerContainer}>
            <p className={styles.font1}>19</p>
            <p className={styles.font5}>
              {dayjs(startTime).format('YYYY년 M월')}
            </p>
            <p className={styles.font6}>
              {dayjs(startTime).format('HH:mm')}~
              {dayjs(endTime).format('HH:mm')}
            </p>
          </div>
          <div className={styles.upperGoal}>
            <div className={styles.titleContainer}>
              <div className={styles.menuLine}>
                <label htmlFor="dateNoticeTitle">
                  <h4>학습목표</h4>
                </label>
                {userStatus === 'TUTOR' && !isFinished && <DropDown />}
              </div>
              <h5 className={styles.noticeArea}>{dateNoticeTitle}</h5>
            </div>
          </div>
        </section>
        <section className={styles.lowerPart}>
          <div className={styles.noticeHomework}>
            <div className={styles.noticeContainer}>
              <label htmlFor="noticeBody">
                <h4>공지사항</h4>
              </label>
              <p className={styles.noticeArea}>{noticeBody}</p>
            </div>
            <div className={styles.homeworkContainer}>
              <label htmlFor="noticeBody">
                <h4>과제 체크리스트</h4>
              </label>
              <div className={styles.homeworkArea}>
                {homeworks?.map((el) => {
                  return (
                    <div
                      key={el.homeworkId}
                      className={styles.checkBoxContainer}
                    >
                      <CheckBox
                        value={el.homeworkStatus === 'FINISH' ? true : false}
                      />
                      <p className={styles.homeworkBody}>{el.homeworkBody}</p>
                    </div>
                  );
                })}
              </div>
            </div>
          </div>
          <div className={styles.scheduleContainer}>
            <label htmlFor="scheduleBody">
              <h4>수업 상세 및 전달사항</h4>
            </label>
            <p className={styles.scheduleArea}>{scheduleBody}</p>
          </div>
        </section>
      </div>
    </div>
  );
};
JournalForm.propTypes = {
  userData: PropType.object,
};
export default JournalForm;
