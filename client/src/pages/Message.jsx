import styles from './Message.module.css';
import MessageList from '../components/Message/MessageList';
import MessageContent from '../components/Message/MessageContent';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import CurrentRoomIdState from '../recoil/currentRoomId.js';
import ModalState from '../recoil/modal.js';
import { useNavigate } from 'react-router-dom';
import Profile from '../recoil/profile';

const initialState = {
  messageRoomId: 0,
  messages: [
    {
      messageContent: '대화를 선택해주세요',
    },
  ],
};

const Message = () => {
  const [messageList, setMessageList] = useState([0]);
  const [messageRoom, setMessageRoom] = useState(initialState);
  const [pageInfo, setPageInfo] = useState({});
  const CurrentRoomId = useRecoilValue(CurrentRoomIdState);
  const { profileId } = useRecoilValue(Profile);
  const setModal = useSetRecoilState(ModalState);
  const navigate = useNavigate();

  const noMsgAlertModal = {
    isOpen: true,
    modalType: 'handleAlert',
    props: {
      text: `대화상대가 없습니다.

      ${
        sessionStorage.getItem('userStatus') === 'TUTOR' ? '튜티' : '튜터'
      } 프로필의 문의하기 버튼을 눌러서 대화를 시작해주세요.`,
      modalHandler: () => {
        navigate(-1);
      },
    },
  };

  useEffect(() => {
    if (messageList.length !== 0) getMessageRoom();
    else setModal(noMsgAlertModal);
  }, [CurrentRoomId]);

  const getMessageList = async () => {
    await axios
      .get(`/messages/${profileId}`)
      .then((res) => {
        setMessageList(res.data.data);
        setPageInfo(res.data.pageInfo);
        console.log(res.data, '메세지 리스트 API');
      })
      .catch((err) => console.log(err, 'getMessageList'));
  };

  useEffect(() => {
    getMessageList();
  }, []);

  const getMessageRoom = async () => {
    await axios
      .get(`/messages/rooms/${profileId}/${CurrentRoomId}`)
      .then((res) => {
        setMessageRoom(res.data.data);
        console.log(res.data.data, 'MessageRoom API');
      })
      .catch((err) => console.log(err.code, 'getMessageRoom'));
  };

  const delMessageRoom = async () => {
    await axios
      .delete(`/messages/rooms/${CurrentRoomId}`)
      .then(() => {
        window.location.ref('/message');
        console.log('현재 대화방 삭제');
      })
      .catch((err) => console.log(err));
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <h2>메세지함</h2>
        <div className={styles.message}>
          <MessageList
            messageList={messageList}
            setMessageList={setMessageList}
            pageInfo={pageInfo}
            setPageInfo={setPageInfo}
          />
          <MessageContent
            messageRoom={messageRoom}
            getMessageRoom={getMessageRoom}
            delMessageRoom={delMessageRoom}
          />
        </div>
      </div>
    </div>
  );
};

export default Message;
