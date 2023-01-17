import styles from './Message.module.css';
import MessageList from '../components/Message/MessageList';
import MessageContent from '../components/Message/MessageContent';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRecoilState } from 'recoil';
import Profile from '../recoil/profile';

const initialState = {
  messageRoomId: 1,
  targetName: '너에게',
  messages: [
    {
      messageId: 1,
      senderId: 1,
      senderName: '홍길동',
      receiverId: 2,
      receiverName: '김코딩',
      messageContent: 'messageId1~',
      createAt: '2023-01-10T10:53:13.9958657',
    },
    {
      messageId: 2,
      senderId: 2,
      senderName: '김코딩',
      receiverId: 1,
      receiverName: '홍길동',
      messageContent:
        '아버지를 아버지라 부르지 못하고 아버지를 아버지라 부르지 못하고 아버지를 아버지라 부르지 못하고 아버지를 아버지라 부르지 못하고 아버지를 아버지라 부르지 못하고 아버지를 아버지라 부르지 못하고 아버지를 아버지라 부르지 못하고 ',
      createAt: '2023-01-10T10:53:13.9958657',
    },
    {
      messageId: 3,
      senderId: 1,
      senderName: '홍길동',
      receiverId: 2,
      receiverName: '김코딩',
      messageContent: '아버지를 아버지라 부르지 못하고',
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

  const [profile] = useRecoilState(Profile);
  const [messageList, setMessageList] = useState([]);
  const [messageRoom, setMessageRoom] = useState({});
  //* 대체
  // const [currentRoomId, setCurrentRoomId] = useState(
  //   messageList[0].messageRoomId
  // );
  const [currentRoomId, setCurrentRoomId] = useState('');

  const getMessageList = async () => {
    await axios
      .get(`${process.env.REACT_APP_BASE_URL}/messages/${profile.profileId}`, {
        headers: headers,
      })
      .then((res) => {
        setMessageList(res.data.data);
        console.log(res.data.data, 'getMessageList');
      })
      .catch((err) => console.log(err));
  };

  useEffect(
    () => {
      getMessageList();
      getMessageRoom();
    },
    messageList,
    messageRoom,
    currentRoomId
  );

  const getMessageRoom = async () => {
    await axios
      //* 대체
      // .get(
      //   `${process.env.REACT_APP_BASE_URL}/rooms/${profile.profileId}/${currentRoomId}`,
      //   {
      //     headers: headers,
      //   }
      // )
      .get(`${process.env.REACT_APP_BASE_URL}/rooms/1/1`, {
        headers: headers,
      })
      .then((res) => setMessageRoom(res.data.data))
      .catch((err) => console.log(err, 'getMessageRoom'));
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <h2>메세지함</h2>
        <div className={styles.message}>
          <MessageList
            messageList={messageList}
            setCurrentRoomId={setCurrentRoomId}
          />
          <MessageContent
            //* 대체 initialState -> messageRoom
            messages={initialState.messages}
            currentRoomId={currentRoomId}
          />
        </div>
      </div>
    </div>
  );
};

export default Message;
