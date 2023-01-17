import PropType from 'prop-types';
import styles from './MessageContent.module.css';

// * 채팅 메세지 컴포넌트
const Chat = ({ message, authId }) => {
  const { senderId, messageContent, senderName, createAt } = message;

  return (
    <div
      className={`${styles.chatContainer} ${
        senderId === authId ? styles.sendChat : undefined
      }`}
    >
      {senderId === authId ? undefined : <h5>{senderName}</h5>}
      <p>{messageContent}</p>
      {/* TODO : createdAt 변환 작업  필요 */}
      <span>AM 01:00</span>
    </div>
  );
};

Chat.propTypes = {
  authId: PropType.number,
  message: PropType.object,
};

export default Chat;
