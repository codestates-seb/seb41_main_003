import styles from './FinishedJournalList.module.css';
import dummyTutoringData from './dummyTutoringData';
import { HiSpeakerphone } from 'react-icons/hi';
import { MdEdit } from 'react-icons/md';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { ConfirmModal, ConfirmTextModal } from '../Modal';
import { useState } from 'react';

const FinishedJournalList = () => {
  const [confirmModalData, setConfirmModalData] = useState({
    text: `과외를 삭제 하시겠습니까?
    과외 삭제 시 과외 관리 내역이 모두 삭제됩니다.`,
    isOpen: false,
  });
  const [confirmTextModal, setConfirmTextModal] = useState({
    text: '튜터링 이름을 아래와 같이 수정합니다.',
    isOpen: false,
    placeHolder: 'Text',
  });
  const [newTitle, setNewTitle] = useState('');

  const {
    tutoringTitle,
    tuteeName,
    tutorName,
    dateNotices,
    updateAt,
    createAt,
    latestNotice,
  } = dummyTutoringData;

  // TODO: ConfirmModal에서 확인을 누르면 삭제 요청 가도록 MessageContent 컴포넌트 참고해서 로직 추가
  //근데 그 전에 삭제 요청에 대한 백단과 이야기 필요함 (한 쪽이 삭제 시 과외 했던 양쪽에서 삭제될듯)
  const confirmHandler = () => {
    setConfirmModalData(!confirmModalData.isOpen);
  };

  const confirmTextHandler = () => {
    setConfirmTextModal(!confirmTextModal.isOpen);
  };

  // TODO: Title 수정 모달에서 새로운 제목 입력 후 확인을 누르면 수정 요청 가도록
  //MessageContent 컴포넌트 참고해서 로직 추가
  const titleHandler = ({ target }) => {
    setNewTitle(target.value);
  };

  //TODO:현재 이 컴포넌트는 튜티 기준으로 만들어짐 이후 튜터, 튜티 분기해 수정 필요
  //name부분과 과외 일지 작성 버튼 부분이 튜터와 튜티가 달라야 함
  // 작성된 리뷰 확인 버튼은 튜티인 경우에만 존재함

  return (
    <div className={styles.container}>
      <div className={styles.leftCard}>
        <div className={styles.noti}>
          <HiSpeakerphone className={styles.icon} />
          {latestNotice > 20
            ? `최근 공지사항 | ${latestNotice.slice(0, 20)}...`
            : `최근 공지사항 | ${latestNotice}`}
        </div>
        <ul className={styles.list}>
          {dateNotices.map((el) => (
            <li key={el.dateNoticeId} className={styles.li}>
              <div className={styles.dateBox}>
                <span className={styles.day}>
                  {new Date(el.startTime).getDate()}
                </span>
                <span className={styles.yearMonth}>{`${new Date(
                  el.startTime
                ).getFullYear()}년 ${
                  new Date(el.startTime).getMonth() + 1
                }월`}</span>
              </div>
              <div className={styles.textBox}>
                <span
                  className={styles.goal}
                >{`학습 목표 | ${el.dateNoticeTitle} `}</span>
                <span
                  className={styles.homework}
                >{`과제 제출 완료 (${el.finishHomeworkCount}/${el.homeworkCount})`}</span>
              </div>
              {/* TODO: 최근 공지를 눌렀을 때 해당 공지가 있는 일지 상세 페이지로 이동해야 함  */}
              <div className={styles.notiIcon}>
                <HiSpeakerphone className={styles.hiSpeaker} />
                공지
              </div>
            </li>
          ))}
        </ul>
      </div>
      <div className={styles.rightCard}>
        <div className={styles.rightTextBox}>
          <div className={styles.nameBox}>
            {/* TODO: user status에 따라 tutorName이 뜨거나 tuteeName이 떠야 함  */}
            <span>{tutorName}</span>
            <button onClick={confirmTextHandler}>
              <MdEdit className={styles.mdEdit} />
              <span>제목 수정하기</span>
            </button>
            {confirmTextModal.isOpen && (
              <ConfirmTextModal
                text={confirmTextModal.text}
                modalHandler={confirmTextHandler}
                value={newTitle}
                valueHandler={titleHandler}
              />
            )}
          </div>
          <span className={styles.tutoringTitle}>{tutoringTitle}</span>
          <span className={styles.tutoringDate}>
            {`${new Date(createAt).toLocaleDateString()} ~ ${new Date(
              updateAt
            ).toLocaleDateString()}`}
          </span>
        </div>
        <div className={styles.buttonBox}>
          {/* TODO: 버튼 눌렀을 때 리뷰 확인 모달 띄워져야 함  */}
          <ButtonNightBlue text="작성된 리뷰 확인" />
          {/* TODO: 버튼 눌렀을 때 과외 삭제 안내 모달 떠야 함 */}
          <ButtonRed text="과외 삭제" buttonHandler={confirmHandler} />
          {confirmModalData.isOpen && (
            <ConfirmModal
              text={confirmModalData.text}
              modalHandler={confirmHandler}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default FinishedJournalList;
