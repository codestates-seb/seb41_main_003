import PropType from 'prop-types';
import styles from './Chat.module.css';

const Chat = ({ message, authId }) => {
  const { senderId, messageContent, senderName, createAt } = message;

  return (
    <div
      className={`${styles.chatContainer} ${
        senderId === authId ? styles.sendChat : undefined
      }`}
    >
      {senderId === authId ? undefined : <h5>{senderName}</h5>}

      {messageContent === 'REQ_UEST' ? (
        senderId === authId ? (
          <div className={styles.sendRequestBox}>
            <p>매칭 요청을 보냈습니다.</p>
            <button className={styles.requestCancelBtn}>요청 취소하기</button>
          </div>
        ) : (
          <div className={styles.receiveRequestBox}>
            <p>매칭 요청이 도착했습니다.</p>
            <button className={styles.checkRequestBtn}>요청 확인하기</button>
          </div>
        )
      ) : (
        <p className={styles.text}>{messageContent}</p>
      )}

      <span className={styles.time}>
        {Number(createAt.slice(11, 13)) >= 12 ? 'PM' : 'AM'}{' '}
        {createAt.slice(11, 16)}
      </span>
    </div>
  );
};

Chat.propTypes = {
  authId: PropType.number,
  message: PropType.object,
};

export default Chat;
