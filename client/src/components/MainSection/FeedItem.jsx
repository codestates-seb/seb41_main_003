import styles from './FeedItem.module.css';
import PropTypes from 'prop-types';
import { BlueSubject } from '../Subject';
import { MdStar, MdStarHalf, MdStarOutline } from 'react-icons/md';

const FeedItem = ({ data, userStatus }) => {
  const { name, school, bio, subjects, rate, profileImage, profileId } = data;
  return (
    <div className={styles.container}>
      <img className={styles.img} alt="프로필 이미지" src={profileImage?.url} />
      <div className={styles.nameAndStars}>
        <div className={styles.name}>
          {userStatus === 'TUTOR' ? `${name} 튜터` : `${name} 튜티`}
        </div>
        {userStatus === 'TUTOR' && (
          <div className={styles.stars}>
            {new Array(5).fill('star').map((_, count) => {
              const star = Number(rate);
              const checkedStar = (star) => {
                if (count + 1 - 0.5 === star) return 'half';
                else if (count + 1 > star) return 'dimmed';
                else return 'colored';
              };

              const renderStar = (color) => {
                switch (color) {
                  case 'dimmed':
                    return (
                      <MdStarOutline key={count} className={styles.emptyStar} />
                    );
                  case 'half':
                    return (
                      <MdStarHalf key={count} className={styles.halfStar} />
                    );
                  default:
                    return <MdStar key={count} className={styles.yellowStar} />;
                }
              };
              return renderStar(checkedStar(star));
            })}
          </div>
        )}
      </div>
      <div className={styles.subjectBox}>
        {subjects.map((el, i) => (
          <div key={`${profileId}_subject${i}`} className={styles.subject}>
            <BlueSubject text={el.subjectTitle} />
          </div>
        ))}
      </div>
      <span className={styles.school}>{school}</span>
      <div className={styles.bio}>
        {bio.length > 16 ? `${bio.slice(0, 16)}...` : bio}
      </div>
    </div>
  );
};

FeedItem.propTypes = {
  data: PropTypes.object,
  userStatus: PropTypes.string,
};

export default FeedItem;
