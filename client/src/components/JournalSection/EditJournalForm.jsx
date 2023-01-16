import styles from './EditJournalForm.module.css';
import PropType from 'prop-types';
import { useState } from 'react';
import DatePickerForm from './DatePickerForm';
import { Textarea, TextInput, CheckBox } from '../Input';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';
import { MdDelete } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

const EditJournalForm = ({ user, setUser }) => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const Navigate = useNavigate();

  const [homeworkVal, setHomeworkVal] = useState('');

  const { dateNoticeId, dateNoticeTitle, scheduleBody, noticeBody, Homeworks } =
    user;

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `현재 입력된 내용으로
      일지를 수정 하시겠습니까?`,
      modalHandler: () => {
        //TODO:서버에 user 정보 수정 patch
        //TODO:??수정 완료 하고 나면 수정완료 버튼으로 변경
        //TODO: 해당 프로필Id ,dateNoticeId의 일지 페이지로 이동 (useParam)
        console.log('일지 수정 확인, patch요청');
        setModal(confirmProps);
        resetModal();
        Navigate(`/journal`);
      },
    },
  };

  const confirmProps = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: '일지가 수정 되었습니다.',
    },
  };

  const cancel = {
    isOpen: true,
    modalType: 'redConfirm',
    props: {
      text: `취소 하시겠습니까?
      작성 중인 내용이 모두 사라집니다.`,
      modalHandler: () => {
        //TODO: 해당 프로필Id,dateNoticeId의 일지 수정 페이지로 이동 (useParam)
        console.log('일지 수정 취소');
        resetModal();
        Navigate('/journal');
        setModal(redAlertModal);
      },
    },
  };

  const redAlertModal = {
    isOpen: true,
    modalType: 'redAlert',
    props: {
      text: '일지가 수정이 취소되었습니다.',
    },
  };

  const inputHandler = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const deleteHomeworkHandler = (e) => {
    const { name } = e.target;

    setUser({
      ...user,
      Homeworks: Homeworks.filter((obj) => obj.homeworkId !== e.target.value),
    });
  };

  const addHomeworkHandler = (e) => {
    if (e.key === 'Enter' && e.target.value) {
      setUser({
        ...user,
        Homeworks: [
          ...Homeworks,
          {
            homeworkId: Homeworks[Homeworks.length - 1].homeworkId + 1,
            homeworkBody: homeworkVal,
            HomeworkStatus: 'PROGRESS',
          },
        ],
      });
      setHomeworkVal('');
    }
  };

  return (
    <div className={styles.container}>
      <h1>과외 일지 작성</h1>
      <div className={styles.journalContainer}>
        <div className={styles.buttonContainer}>
          <ButtonRed text="취소" buttonHandler={() => setModal(cancel)} />
          <ButtonNightBlue
            text="작성 완료"
            buttonHandler={() => setModal(confirm)}
          />
        </div>
        <section className={styles.upperPart}>
          <DatePickerForm setUser={setUser} user={user} />
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
              <ul className={styles.homeworkArea}>
                {Homeworks.map(
                  ({ homeworkId, homeworkBody, HomeworkStatus }) => {
                    const checked = HomeworkStatus === 'FINISHED';
                    return (
                      <li key={homeworkId} className={styles.checkBoxContainer}>
                        <CheckBox
                          id={`homework${homeworkId}`}
                          handler={() =>
                            setUser({
                              ...user,
                              Homeworks: user.Homeworks.map((homework) => {
                                return homework.homeworkId === homeworkId
                                  ? {
                                      ...homework,
                                      HomeworkStatus:
                                        homework.HomeworkStatus === 'FINISHED'
                                          ? 'PROGRESS'
                                          : 'FINISHED',
                                    }
                                  : homework;
                              }),
                            })
                          }
                          value={checked}
                        />
                        <div className={styles.homeworkBody}>
                          {homeworkBody}
                        </div>
                        <div className={styles.homeworkMenu}>
                          <button
                            id={homeworkId}
                            name={homeworkId}
                            value={homeworkId}
                            onClick={deleteHomeworkHandler}
                          >
                            <MdDelete />
                          </button>
                        </div>
                      </li>
                    );
                  }
                )}
                <div className={styles.addHomework}>
                  <CheckBox />
                  <input
                    value={homeworkVal}
                    onKeyUp={addHomeworkHandler}
                    onChange={(e) => setHomeworkVal(e.target.value)}
                    placeholder="과제를 입력하세요"
                  />
                </div>
              </ul>
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
};
export default EditJournalForm;
