import styles from './Message.module.css';
import MessageList from '../components/Message/MessageList';
import MessageContent from '../components/Message/MessageContent';
import { useState } from 'react';

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
      messageContent: '아버지를 아버지라 부르지 못하고',
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
  // TODO : messageList GET API 이용해서 데이터 받아와야 함
  const [messageList, setMessageList] = useState([]);
  // TODO : message GET API 이용해서 데이터 받아와야 함
  const [messages, setMessages] = useState(initialState.messages);
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <h2>메세지함</h2>
        <div className={styles.message}>
          {/* // TODO : API 나오면 배열 순회해서 출력하도록 수정해야함 */}
          <MessageList />
          <MessageContent messages={messages} />
        </div>
      </div>
    </div>
  );
};

export default Message;
