import styles from './JournalForm.module.css';
import PropType from 'prop-types';
import { ButtonSilver } from '../Button';
import { CheckBox } from '../Input';
import { useState } from 'react';
import DropDown from './DropDown';

const JournalForm = ({ user, setUser }) => {
  const {
    dateNoticeId,
    dateNoticeTitle,
    startTime,
    endTime,
    scheduleBody,
    noticeBody,
    Homeworks,
  } = user;
  // const [isChecked, setIsChecked] = useState();
  // const checkHandler = () => {
  //   setIsChecked();
  // };
  return (
    <div className={styles.container}>
      <h1>과외 일지 </h1>
      <div className={styles.exitButton}>
        <ButtonSilver text="나가기" />
      </div>

      <div className={styles.journalContainer}>
        <section className={styles.upperPart}>
          <div className={styles.pickerContainer}>
            <p>05</p>
            <p>2023년 1월</p>
            <p>{startTime}</p>
            <p>{endTime}</p>
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
                      <p className={styles.homeworkBody}>
                        내용:{el.homeworkBody}
                      </p>
                      <p>....Id:{el.homeworkId}</p>
                      <p>....T/F?:{el.HomeworkStatus}</p>
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
