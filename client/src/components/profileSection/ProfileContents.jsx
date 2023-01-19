import PropTypes from 'prop-types';
import TutorContents from './TutorContents';
import TuteeContents from './TuteeContents';

const ProfileContents = ({ user, pageInfo, setPage }) => {
  return (
    <div>
      {user.profileStatus === 'TUTOR' ? (
        <TutorContents user={user} pageInfo={pageInfo} setPage={setPage} />
      ) : (
        <TuteeContents user={user} />
      )}
    </div>
  );
};
ProfileContents.propTypes = {
  user: PropTypes.object,
  pageInfo: PropTypes.object,
  setPage: PropTypes.func,
};

export default ProfileContents;
