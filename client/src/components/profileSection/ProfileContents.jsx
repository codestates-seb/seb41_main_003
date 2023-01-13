import styles from './ProfileContents.module.css';
import PropTypes from 'prop-types';
import { BlueSubject } from '../Subject.jsx';
import { MdStar, MdTextsms } from 'react-icons/md';
import { useRef } from 'react';

const ProfileContentsMDSTAR = () => {
  return <MdStar fill="#F0C24D" size="24px" />;
};

const ProfileContents = ({ user }) => {
  const reviewRef = useRef(null);
  return (
    <div>
      {user.profile_status === 'TUTOR' ? (
        <section className={styles.container}>
          <div className={styles.way}>
            <p className={styles.font4}>수업방식</p>
            <p className={styles.paragragh}>{user.way}</p>
          </div>
          <div>
            <div className={styles.subject}>
              <p className={styles.font4}>과목별 수업 내용</p>
              {user.subjects.map((subject) => {
                return (
                  <div className={styles.subjectList} key={subject.subjectId}>
                    <BlueSubject text={subject.subjectTitle} />
                    <p className={styles.paragragh}>{subject.content}</p>
                  </div>
                );
              })}
            </div>
          </div>
          <div>
            <p className={styles.font4}>차별점</p>
            <p className={styles.paragragh}>{user.difference}</p>
          </div>
          <div>
            <p className={styles.font4}>성별</p>
            <p className={styles.paragragh}>{user.gender}</p>
          </div>
          <div>
            <p className={styles.font4}>성격</p>
            <p className={styles.paragragh}>{user.character}</p>
          </div>
          <div>
            <p className={styles.font4}>수업료</p>
            <p className={styles.paragragh}>{user.pay}</p>
          </div>
          <div>
            <p className={styles.font4}>과외 가능 요일 / 시간</p>
            <p className={styles.paragragh}>{user.want_date}</p>
          </div>
          <div>
            <p className={styles.font4}>시범 과외 가능 여부</p>
            <p className={styles.paragragh}>{user.pre_tutoring}</p>
          </div>
          <div className={styles.reviewContainer} ref={reviewRef}>
            <div className={styles.reviewTitleLine}>
              <p className={styles.font3}>후기</p>
              <p className={styles.font5}>총 {user.reviews.length}개의 후기</p>
            </div>
            <hr />
            <div>
              {user.reviews.map((review) => {
                return (
                  <div
                    key={review.reviewId}
                    className={styles.reviewContentBox}
                  >
                    <p className={styles.reviewProfileId}>{review.tuteeName}</p>
                    <div className={styles.rating}>
                      <div className={styles.flexbox}>
                        <p>
                          전문성
                          <span className={styles.stars}>
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                          </span>
                        </p>
                        <p>
                          준비성
                          <span className={styles.stars}>
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                          </span>
                        </p>
                      </div>
                      <div className={styles.flexbox}>
                        <p>
                          설명력
                          <span className={styles.stars}>
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                          </span>
                        </p>
                        <p>
                          시간 준수
                          <span className={styles.stars}>
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                            <ProfileContentsMDSTAR />
                          </span>
                        </p>
                      </div>
                    </div>
                    <p className={styles.reviewBody}>{review.reviewBody}</p>
                  </div>
                );
              })}
            </div>
          </div>
          <button
            className={styles.reviewBtn}
            onClick={() =>
              reviewRef.current.scrollIntoView({
                behavior: 'smooth',
                block: 'start',
              })
            }
          >
            과외 후기 <MdTextsms />
          </button>
        </section>
      ) : (
        <section className={styles.container}>
          <div>
            <p className={styles.font4}>성격</p>
            <p className={styles.paragragh}>{user.character}</p>
          </div>
          <div>
            <p className={styles.font4}>성별</p>
            <p className={styles.paragragh}>{user.gender}</p>
          </div>

          <div>
            <p className={styles.font4}>예산</p>
            <p className={styles.paragragh}>{user.pay}</p>
          </div>
          <div>
            <p className={styles.font4}>과외 가능 요일 / 시간대</p>
            <p className={styles.paragragh}>{user.want_date}</p>
          </div>
          <div>
            <p className={styles.font4}>시범 과외 가능 여부</p>
            <p className={styles.paragragh}>{user.pre_tutoring}</p>
          </div>
        </section>
      )}
    </div>
  );
};
ProfileContents.propTypes = {
  user: PropTypes.object,
};

export default ProfileContents;
