import PropTypes from 'prop-types';
import TutorContents from './TutorContents';
import TuteeContents from './TuteeContents';

const ProfileContents = ({ user }) => {
  return (
    <div>
      {user.profileStatus === 'TUTOR' ? (
        <TutorContents user={user} />
      ) : (
        <TuteeContents user={user} />
      )}
    </div>
  );
};
ProfileContents.propTypes = {
  user: PropTypes.object,
};

export default ProfileContents;
