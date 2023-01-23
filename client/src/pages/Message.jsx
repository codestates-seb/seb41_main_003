import styles from './Message.module.css';
import MessageList from '../components/Message/MessageList';
import MessageContent from '../components/Message/MessageContent';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import CurrentRoomIdState from '../recoil/currentRoomId.js';
import ModalState from '../recoil/modal.js';

const initialState = {
  messageRoomId: 0,
  messages: [
    {
      messageContent: '대화를 선택해주세요',
    },
  ],
};

const Message = () => {
  const [messageList, setMessageList] = useState([]);
  const [messageRoom, setMessageRoom] = useState(initialState);
  const CurrentRoomId = useRecoilValue(CurrentRoomIdState);
  const profileId = JSON.parse(localStorage.getItem('current_user')).profileId;

  const setModal = useSetRecoilState(ModalState);

  const noMsgAlertModal = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: `대화상대가 없습니다.

      ${
        sessionStorage.getItem('userStatus') === 'TUTOR' ? '튜티' : '튜터'
      } 프로필의 문의하기 버튼을 눌러서 대화를 시작해주세요.`,
    },
  };

  useEffect(() => {
    getMessageList();
  }, []);

  useEffect(() => {
    getMessageRoom();
  }, [CurrentRoomId]);

  const getMessageList = async () => {
    await axios
      .get(`/messages/${profileId}`)
      .then((res) => {
        setMessageList(res.data.data);
        console.log(res.data.data, 'getMessageList API');
      })
      .catch((err) => console.log(err));
  };

  const getMessageRoom = async () => {
    await axios
      .get(`/messages/rooms/${profileId}/${CurrentRoomId}`)
      .then((res) => {
        setMessageRoom(res.data.data);
        console.log(res.data.data, 'MessageRoom API');
      })
      .catch((err) => console.log(err.code));
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
          <MessageList messageList={messageList} />
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
