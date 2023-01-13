import styles from './EditJournalForm.module.css';
import PropType from 'prop-types';
import DatePickerForm from './DatePickerForm';
import { Textarea, TextInput, CheckBox } from '../Input';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useState } from 'react';

const EditJournalForm = ({ user, setUser, confirmHandler }) => {
  const [isChecked, setIsChecked] = useState(false);
  const {
    dateNoticeId,
    dateNoticeTitle,
    startTime,
    endTime,
    scheduleBody,
    noticeBody,
    Homeworks,
  } = user;

  const inputHandler = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
    // name 키를 가진 값을 value로 대체
  };

  const homeworkInputHandler = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const checkHandler = (e) => {
    const { id, value } = e.target;
    // 맵 도는중이고 id 1이랑 밸류 트루 들고 왔어
    // setUser({ ...user, Homeworks[id].HomeworkStatus: value });
    // const new = {

    // }
    setUser({ ...user, [id]: value });
  };
  const blueButtonHandler = () => {};

  return (
    <div className={styles.container}>
      <h1>과외 일지 작성</h1>
      <div className={styles.journalContainer}>
        <div className={styles.buttonContainer}>
          <ButtonRed text="취소" buttonHandler={confirmHandler} />
          <ButtonNightBlue text="작성 완료" buttonHandler={blueButtonHandler} />
        </div>
        <section className={styles.upperPart}>
          <DatePickerForm />
          <div className={styles.upperGoal}>
            <div className={styles.titleContainer}>
              <label htmlFor="dateNoticeTitle">
                <h4>학습목표</h4>
              </label>
              <div className={styles.titleInput}>
                <TextInput
                  id="dateNoticeTitle"
                  handler={inputHandler}
                  value={dateNoticeTitle}
                  placeHolder="학습 목표를 입력하세요"
                />
              </div>
            </div>
          </div>
        </section>
        <section className={styles.lowerPart}>
          <div className={styles.noticeHomework}>
            <div className={styles.noticeContainer}>
              <label htmlFor="noticeBody">
                <h4>공지사항</h4>
              </label>
              <div className={styles.noticeArea}>
                <Textarea
                  id="noticeBody"
                  handler={inputHandler}
                  value={noticeBody}
                  placeHolder="공지할 사항을 입력하세요"
                />
              </div>
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
                      <CheckBox
                        id={el.homeworkId}
                        handler={checkHandler}
                        value={el.HomeworkStatus}
                      />
                      <div className={styles.homeworkBody}>
                        내용::{el.homeworkBody}
                      </div>
                      <div>....Id:{el.homeworkId}</div>
                      <div>....T/F{el.HomeworkStatus}</div>
                    </div>
                  );
                })}
                <div className={styles.addHomework}>
                  <CheckBox />
                  <input
                    onChange={homeworkInputHandler}
                    placeholder="과제를 입력하세요"
                  ></input>
                </div>
              </div>
            </div>
          </div>
          <div className={styles.scheduleContainer}>
            <label htmlFor="scheduleBody">
              <h4>수업 상세 및 전달사항</h4>
            </label>
            <div className={styles.scheduleArea}>
              <Textarea
                id="scheduleBody"
                handler={inputHandler}
                value={scheduleBody}
                placeHolder="Text"
              />
            </div>
          </div>
        </section>
      </div>
    </div>
  );
};
EditJournalForm.propTypes = {
  user: PropType.object,
  setUser: PropType.func,
  confirmHandler: PropType.func,
};
export default EditJournalForm;
