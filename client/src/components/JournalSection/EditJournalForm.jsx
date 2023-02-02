import styles from './EditJournalForm.module.css';
import { useState } from 'react';
import DatePickerForm from './DatePickerForm';
import { Textarea, TextInput, CheckBox } from '../Input';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState, useRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';
import { MdDelete } from 'react-icons/md';
import { useNavigate, useLocation } from 'react-router-dom';
import ChangeJournal from '../../recoil/journal';
import axios from 'axios';

const EditJournalForm = () => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const resetJournal = useResetRecoilState(ChangeJournal);
  const [userData, setUserData] = useRecoilState(ChangeJournal);
  const [homeworkVal, setHomeworkVal] = useState('');
  const navigate = useNavigate();

  const { dateNoticeId, tutoringId } = useLocation().state;
  const { pathname } = useLocation();
  const isAdd = pathname.includes('addjournal');

  const { dateNoticeTitle, scheduleBody, noticeBody, homeworks } = userData;

  const submitHandler = async () => {
    await axios[isAdd ? 'post' : 'patch'](
      `/tutoring/date-notice/${isAdd ? tutoringId : dateNoticeId}`,
      userData
    ).catch((err) => console.log(err));
  };

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `현재 입력된 내용으로 일지를 ${
        isAdd ? '작성' : '수정'
      }하시겠습니까?`,
      modalHandler: () => {
        submitHandler();
        setModal(confirmProps);
      },
    },
  };

  const confirmProps = {
    isOpen: true,
    modalType: 'handlerAlert',
    props: {
      text: `일지가 ${isAdd ? '작성' : '수정'} 되었습니다.`,
      modalHandler: () => {
        isAdd
          ? navigate(`/tutoring`, {
              state: { tutoringId },
            })
          : navigate(`/journal`, { state: { dateNoticeId, tutoringId } });
        resetJournal();
        resetModal();
      },
    },
  };

  const cancel = {
    isOpen: true,
    modalType: 'redConfirm',
    props: {
      text: `취소 하시겠습니까?
      작성 중인 내용이 모두 사라집니다.`,
      modalHandler: () => {
        isAdd
          ? navigate(`/tutoring`, { state: { tutoringId } })
          : navigate(`/journal`, { state: { dateNoticeId, tutoringId } });
        resetJournal();
        resetModal();
      },
    },
  };

  const inputHandler = (e) => {
    const { name, value } = e.target;
    // if (name === 'scheduleBody') {
    //   setUserData({
    //     ...userData,
    //     [scheduleBody]: userData[scheduleBody].replace(/(\n|\r\n)/g, '<br>'),
    //   });
    // } else {
    setUserData({ ...userData, [name]: value });
    // }
  };

  const deleteHomeworkHandler = (e) => {
    const { id } = e.currentTarget;
    setUserData({
      ...userData,
      homeworks: homeworks.filter((el) => {
        return el.homeworkId !== Number(id);
      }),
    });
  };

  const addHomeworkHandler = (e) => {
    if (e.key === 'Enter' && e.target.value) {
      setUserData({
        ...userData,
        homeworks: [
          ...homeworks,
          {
            homeworkId: homeworks.length
              ? homeworks[homeworks.length - 1].homeworkId + 1
              : 1,
            homeworkBody: homeworkVal,
            homeworkStatus: 'PROGRESS',
          },
        ],
      });
      setHomeworkVal('');
    }
  };

  return (
    <div className={styles.container}>
      <h1>과외 일지 {isAdd ? '작성' : '수정'}</h1>
      <div className={styles.journalContainer}>
        <div className={styles.buttonContainer}>
          <span className={styles.required}>
            <span className={styles.requiredIcon} />은 필수 입력 사항입니다.
          </span>
          <ButtonRed text="취소" buttonHandler={() => setModal(cancel)} />
          <ButtonNightBlue
            text={`${isAdd ? '작성' : '수정'}`}
            buttonHandler={() => {
              if (dateNoticeTitle.length !== 0 && scheduleBody.length !== 0)
                setModal(confirm);
              else
                setModal({
                  isOpen: true,
                  modalType: 'alert',
                  props: {
                    text: `학습목표와 수업 상세 및 전달 사항은 필수로 작성해야 합니다.`,
                  },
                });
            }}
          />
        </div>
        <section className={styles.upperPart}>
          <DatePickerForm />
          <div className={styles.upperGoal}>
            <div className={styles.titleContainer}>
              <label htmlFor="dateNoticeTitle">
                <h4>
                  학습목표
                  <span className={styles.requiredIcon} />
                </h4>
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
                {homeworks &&
                  homeworks.map(
                    ({ homeworkId, homeworkBody, homeworkStatus }) => {
                      const checked = homeworkStatus === 'FINISH';
                      return (
                        <li
                          key={`homework${homeworkId}`}
                          className={styles.checkBoxContainer}
                        >
                          <CheckBox
                            id={`homework${homeworkId}`}
                            handler={() =>
                              setUserData({
                                ...userData,
                                homeworks: userData.homeworks.map(
                                  (homework) => {
                                    return homework.homeworkId === homeworkId
                                      ? {
                                          ...homework,
                                          homeworkStatus:
                                            homework.homeworkStatus === 'FINISH'
                                              ? 'PROGRESS'
                                              : 'FINISH',
                                        }
                                      : homework;
                                  }
                                ),
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
              <h4>
                수업 상세 및 전달사항
                <span className={styles.requiredIcon} />
              </h4>
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

export default EditJournalForm;
