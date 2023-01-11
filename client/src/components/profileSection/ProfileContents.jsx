import styles from './ProfileContents.module.css';
import PropTypes from 'prop-types';
import { BlueSubject } from '../Subject.jsx';
import { MdStar } from 'react-icons/md';

const ProfileContentsMDSTAR = () => {
  return <MdStar fill="#F0C24D" size="24px" />;
};

const ProfileContents = ({ user }) => {
  return (
    <div>
      {user.profile_status === 'TUTOR' ? (
        <main className={styles.container}>
          <div className={styles.way}>
            <div className={styles.font4}>수업방식</div>
            <div className={styles.paragragh}>{user.way}</div>
          </div>
          <div>
            <div className={styles.font4}>과목별 수업 내용</div>
            <div className={styles.subject}>
              {user.subjects.map((subject) => {
                return (
                  <div className={styles.subjectList} key={subject.subjectId}>
                    <BlueSubject text={subject.subjectTitle} />
                    <div className={styles.paragragh}>{subject.content}</div>
                  </div>
                );
              })}
            </div>
          </div>
          <div>
            <div className={styles.font4}>차별점</div>
            <div className={styles.paragragh}>{user.difference}</div>
          </div>
          <div>
            <div className={styles.font4}>성별</div>
            <div className={styles.paragragh}>{user.gender}</div>
          </div>
          <div>
            <div className={styles.font4}>성격</div>
            <div className={styles.paragragh}>{user.character}</div>
          </div>
          <div>
            <div className={styles.font4}>수업료</div>
            <div className={styles.paragragh}>{user.pay}</div>
          </div>
          <div>
            <div className={styles.font4}>과외 가능 요일 / 시간</div>
            <div className={styles.paragragh}>{user.want_date}</div>
          </div>
          <div>
            <div className={styles.font4}>시범 과외 가능 여부</div>
            <div className={styles.paragragh}>{user.pre_tutoring}</div>
          </div>
          <div className={styles.review}>
            <div className={styles.reviewTitleLine}>
              <div className={styles.font3}>후기</div>
              <div className={styles.font5}>총 {''}2개의 후기</div>
            </div>
            <hr />

            <>
              <div className={styles.reviewContentBox}>
                <div className={styles.reviewProfileId}>박학생</div>
                <div className={styles.rating}>
                  <div className={styles.flexbox}>
                    <div className={styles.reviewProfessional}>
                      전문성
                      <span className={styles.stars}>
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                      </span>
                    </div>
                    <div className={styles.reviewReadiness}>
                      준비성
                      <span className={styles.stars}>
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                      </span>
                    </div>
                  </div>
                  <div className={styles.flexbox}>
                    <div className={styles.reviewExplanation}>
                      설명력
                      <span className={styles.stars}>
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                      </span>
                    </div>
                    <div className={styles.reviewPunctuality}>
                      시간 준수
                      <span className={styles.stars}>
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                        <ProfileContentsMDSTAR />
                      </span>
                    </div>
                  </div>
                </div>
                <div className={styles.reviewBody}>
                  우리 남편 영양간식 우리 아이 술 안주. 키야 쥑인다 무바라 함
                  무봤나 한번 맛보면 잊을수가 읍따 이 맛을 모르고 우예 사노
                  안글나?
                </div>
              </div>
            </>
          </div>
        </main>
      ) : (
        <main className={styles.container}>
          <div>
            <div className={styles.font4}>성격</div>
            <div className={styles.paragragh}>{user.character}</div>
          </div>
          <div>
            <div className={styles.font4}>성별</div>
            <div className={styles.paragragh}>{user.gender}</div>
          </div>

          <div>
            <div className={styles.font4}>예산</div>
            <div className={styles.paragragh}>{user.pay}</div>
          </div>
          <div>
            <div className={styles.font4}>과외 가능 요일 / 시간대</div>
            <div className={styles.paragragh}>{user.want_date}</div>
          </div>
          <div>
            <div className={styles.font4}>시범 과외 가능 여부</div>
            <div className={styles.paragragh}>{user.pre_tutoring}</div>
          </div>
        </main>
      )}
    </div>
  );
};
ProfileContents.propTypes = {
  user: PropTypes.object,
};

export default ProfileContents;
