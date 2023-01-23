import styles from './ProfileCard.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button.jsx';
import { BlueSubject } from '../Subject.jsx';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import { useNavigate, useParams } from 'react-router-dom';
import ModalState from '../../recoil/modal';
import { MdStar } from 'react-icons/md';
import axios from 'axios';
import Profile from '../../recoil/profile';

const ProfileCard = ({ user }) => {
  const { name, rate, bio, school, subjects, profileImage } = user;

  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);

  //상대방의 profileId
  const { profileId } = useParams();

  //내 프로필 아이디 myProfileId : 코드 작성 시점에는 0으로 고정되어 있는데,
  //로그인 시 혹은 프로필 전환 시 해당 프로필의 id 값으로 세팅 될 예정
  const myProfileId = useRecoilValue(Profile).profileId;
  const { userStatus } = useRecoilValue(Profile);
  //UserStatus를 꺼내와서 그걸 확인한 다음에 상대가 누구인지 정한다.
  const postData =
    userStatus === 'TUTOR'
      ? { tutorId: myProfileId, tuteeId: Number(profileId) }
      : { tutorId: Number(profileId), tuteeId: myProfileId };

  const navigate = useNavigate();

  const postNewMessageRoom = async () => {
    await axios
      .post(`/messages/${myProfileId}`, postData)
      .then(() => navigate(`/message`))
      .catch(({ response }) => {
        console.log(response);
        console.log(response.status);
        console.log(response.data.message);
      });
  };

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '상대방에게 문의를 요청하시겠습니까?',
      modalHandler: () => {
        postNewMessageRoom();
        navigate(`/message`);
        reset();
      },
    },
  };

  return (
    <div className={styles.cardContainer}>
      <img alt="user img" src={profileImage?.url} />
      <section className={styles.textContainer}>
        <div className={styles.starLine}>
          <h3 className={styles.font1}>{name}</h3>
          <div className={styles.starBox}>
            <MdStar fill="#F0C24D" size="21" />
            <p className={styles.paragragh}>{rate} / 5 </p>
          </div>
        </div>
        <div>
          <p className={styles.font4}>한 줄 소개</p>
          <p className={styles.paragragh}>{bio}</p>
        </div>
        <div>
          <p className={styles.font4}>학교</p>
          <p className={styles.paragragh}>{school}</p>
        </div>
        <div>
          <p className={styles.font4}>과목</p>
          <span className={styles.subjects}>
            <ul>
              {subjects.map(({ subjectId, subjectTitle }) => {
                return <BlueSubject key={subjectId} text={subjectTitle} />;
              })}
            </ul>
          </span>
        </div>
      </section>
      {Number(profileId) !== myProfileId &&
        !location.pathname.includes(userStatus.toLowerCase()) && (
          <div className={styles.buttonBox}>
            <ButtonNightBlue
              buttonHandler={(e) => {
                e.preventDefault();
                setModal(confirm);
              }}
              text="문의하기"
            />
          </div>
        )}
    </div>
  );
};

ProfileCard.propTypes = {
  user: PropTypes.object,
};

export default ProfileCard;
