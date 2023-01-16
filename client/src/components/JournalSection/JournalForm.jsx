import styles from './JournalForm.module.css';
import PropType from 'prop-types';
import { ButtonSilver } from '../Button';
import { CheckBox } from '../Input';
import DropDown from './DropDown';

const JournalForm = ({ user }) => {
  const {
    dateNoticeTitle,
    startTime,
    endTime,
    scheduleBody,
    noticeBody,
    Homeworks,
  } = user;

  return (
    <div className={styles.container}>
      <h1>과외 일지 </h1>
      <div className={styles.journalContainer}>
        <div className={styles.exitButton}>
          <ButtonSilver text="나가기" />
          {/* TODO: 과외 리스트 페이지로 이동 */}
        </div>
        <section className={styles.upperPart}>
          <div className={styles.pickerContainer}>
            <p className={styles.font1}>19</p>
            {/* TODO: 서버에서 받아온  startTime 또는 endTime에서 day 부분만 추출*/}
            <p className={styles.font5}>{startTime}</p>
            <p className={styles.font6}>{endTime}</p>
          </div>
          <div className={styles.upperGoal}>
            <div className={styles.titleContainer}>
              <div className={styles.menuLine}>
                <label htmlFor="dateNoticeTitle">
                  <h4>학습목표</h4>
                </label>
                <DropDown />
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
                {Homeworks.map((el) => {
                  return (
                    <div
                      key={el.homeworkId}
                      className={styles.checkBoxContainer}
                    >
                      <CheckBox value={el.HomeworkStatus} />
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
  user: PropType.object,
  setUser: PropType.func,
};
export default JournalForm;
