import styles from './NoticeItem.module.css';
import PropTypes from 'prop-types';
import { MdCheck, MdClose, MdChatBubble } from 'react-icons/md';
import { BsPersonFill, BsPersonCheckFill } from 'react-icons/bs';
import {
  BiCalendar,
  BiCalendarExclamation,
  BiCalendarCheck,
} from 'react-icons/bi';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import CurrentRoomIdState from '../recoil/currentRoomId';

const NoticeItem = ({ data, setNoticeList, noticeList }) => {
  const { alarmId, alarmStatus, alarmType, profileName, contentId } = data;
  const navigate = useNavigate();
  const setCurrentRoomId = useSetRecoilState(CurrentRoomIdState);

  const changeTypeToPath = (type) => {
    if (type === 'MESSAGE') return '/message';
    if (type === 'TUTORING_REQUEST') return '/message';
    if (type === 'TUTORING_MATCH') return '/tutoringlist';
    if (type === 'DATE_NOTICE') return '/tutoring';
    if (type === 'WAIT_FINISH') return '/tutoring';
    if (type === 'FINISH') return '/tutoring';
  };

  const clickHandler = () => {
    const path = changeTypeToPath(alarmType);
    if (path === '/message') {
      setCurrentRoomId(contentId);
      navigate(path);
    } else {
      navigate(path, {
        state: { tutoringId: contentId },
      });
    }
  };

  const patchAlarm = async () => {
    await axios
      .patch(`/alarm/detail/${alarmId}`)
      .then(() => {
        setNoticeList((prev) => {
          return prev.map((el) =>
            el.alarmId === alarmId ? { ...el, alarmStatus: 'CHECK' } : el
          );
        });
      })
      .catch((err) => console.log(err));
  };

  const deleteAlarm = async () => {
    await axios
      .delete(`/alarm/detail/${alarmId}`)
      .then(() => {
        setNoticeList(noticeList.filter((el) => el.alarmId !== alarmId));
      })
      .catch((err) => console.log(err));
  };

  return (
    <li
      className={`${styles.noticeContainer} ${
        alarmStatus === 'CHECK' ? styles.checked : undefined
      }`}
    >
      {alarmType === 'MESSAGE' ? (
        <button onClick={clickHandler} className={styles.content}>
          <MdChatBubble pull="right" />
          <span>{profileName}?????? ?????? ???????????? ??????????????????.</span>
        </button>
      ) : alarmType === 'TUTORING_REQUEST' ? (
        <button onClick={clickHandler} className={styles.content}>
          <BsPersonFill />
          <span>{profileName}?????? ????????? ??????????????????.</span>
        </button>
      ) : alarmType === 'TUTORING_MATCH' ? (
        <button onClick={clickHandler} className={styles.content}>
          <BsPersonCheckFill />
          <span>{profileName}?????? ????????? ??????????????????.</span>
        </button>
      ) : alarmType === 'DATE_NOTICE' ? (
        <button onClick={clickHandler} className={styles.content}>
          <BiCalendar />
          <span>{profileName}?????? ????????? ????????? ??????????????????.</span>
        </button>
      ) : alarmType === 'WAIT_FINISH' ? (
        <button onClick={clickHandler} className={styles.content}>
          <BiCalendarExclamation />
          <span>{profileName}?????? ?????? ????????? ??????????????????.</span>
        </button>
      ) : alarmType === 'FINISH' ? (
        <button onClick={clickHandler} className={styles.content}>
          <BiCalendarCheck />
          <span>{profileName}?????? ?????? ????????? ??????????????????.</span>
        </button>
      ) : (
        <></>
      )}
      <div className={styles.buttonArea}>
        {alarmStatus === 'UNCHECK' ? (
          <button onClick={patchAlarm}>
            <MdCheck size={20} />
            <span>??????</span>
          </button>
        ) : (
          <></>
        )}
        <button onClick={deleteAlarm}>
          <MdClose size={20} />
          <span>??????</span>
        </button>
      </div>
    </li>
  );
};

NoticeItem.propTypes = {
  data: PropTypes.object,
  setNoticeList: PropTypes.func,
  noticeList: PropTypes.array,
};

export default NoticeItem;
