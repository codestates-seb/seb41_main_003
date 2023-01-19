import styles from './Message.module.css';
import MessageList from '../components/Message/MessageList';
import MessageContent from '../components/Message/MessageContent';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRecoilState, useRecoilValue } from 'recoil';
import CurrentRoomIdState from '../recoil/currentRoomId.js';

// TODO: error 403일때 accessToken 재발급

const initialState = {
  messageRoomId: 0,
  targetName: '너에게',
  messages: [
    {
      messageId: 1,
      senderId: 1,
      senderName: '내프로필',
      receiverId: 0,
      receiverName: '김코딩',
      messageContent: '대화를 선택해주세요..',
      createAt: '2023-01-10T10:53:13.9958657',
    },
  ],
  createAt: '2023-01-10T10:53:13.9958657',
  tutorId: 1,
  tutorName: '너에게',
  tuteeId: 2,
  tuteeName: '나에게',
};

const Message = () => {
  const headers = {
    Authorization: sessionStorage.getItem('authorization'),
  };

  useEffect(() => {
    getMessageList();
  }, []);

  // const [profile] = useRecoilValue(Profile);
  const profile = { profileId: 1 };
  const [CurrentRoomId, setCurrentRoomId] = useRecoilState(CurrentRoomIdState);
  const [messageList, setMessageList] = useState([]);
  const [messageRoom, setMessageRoom] = useState(initialState);

  const getMessageList = async () => {
    await axios
      //`${process.env.REACT_APP_BASE_URL}/messages/${profile.profileId}`
      // `${process.env.REACT_APP_BASE_URL}/messages/1`
      .get(`${process.env.REACT_APP_BASE_URL}/messages/1`, {
        headers: headers,
      })
      .then((res) => {
        setMessageList(res.data.data);
        // setCurrentRoomId(res.data.data[0].messageRoomId);
        console.log(res.data.data, 'getMessageList API');
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getMessageRoom();
  }, [CurrentRoomId]);

  const getMessageRoom = async () => {
    await axios
      //`${process.env.REACT_APP_BASE_URL}/messages/rooms/${profile.profileId}/${currentRoomId}`
      // `${process.env.REACT_APP_BASE_URL}/messages/rooms/1/3`
      .get(
        `${process.env.REACT_APP_BASE_URL}/messages/rooms/${profile.profileId}/${CurrentRoomId}`,
        {
          headers: headers,
        }
      )
      .then((res) => {
        setMessageRoom(res.data.data);
        console.log(res.data.data, 'MessageRoom API');
      })
      .catch((err) => console.log(err.response.status, 'getMessageRoom error'));
  };

  const delMessageRoom = async () => {
    await axios
      .delete(
        `${process.env.REACT_APP_BASE_URL}/messages/rooms/${CurrentRoomId}`,
        {
          headers: headers,
        }
      )
      .then(() => {
        setCurrentRoomId();
        console.log('현재 대화방 삭제');
      })
      .catch((err) => console.log(err));
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <h2>메세지함</h2>
        <button onClick={getMessageList}>getMessageList</button>
        <button onClick={getMessageRoom}>getMessageRoom</button>
        <div className={styles.message}>
          <MessageList messageList={messageList} />
          <MessageContent
            headers={headers}
            profile={profile}
            messageRoom={messageRoom}
            delMessageRoom={delMessageRoom}
            getMessageRoom={getMessageRoom}
          />
        </div>
      </div>
    </div>
  );
};

export default Message;
