#!/bin/perl

use strict;
use warnings;

use constant LOG_DIR => '/var/log/fs_store';
use constant LOG_FILE => 'fs_store.log';
use constant PID_DIR => LOG_DIR;

use Proc::PID_File;
use Proc::Daemon;
use Log::Dispatch;
use Log::Dispatch::File;
use Date::Format;
use File::Spec;

use Net::FTP;
use JSON;

sub dienice ($);

#
# fork and background process
#

our $ME = $0; 
our $PIDFILE = $PIDDIR."/$ME.pid";

startDaemon();

#
# Log agent setup
#

our $HOSTNAME = 'hostname';
chomp $HOSTNAME;
my $log = new Log::Dispatch(
    callbacks => sub { my %h=@_; return Date::Format::time2str('%D %e %T', time).""
    .$HOSTNAME." $0\[$$]: ".$h{message}."\n"; }
);

$log->add( Log::Dispatch::File->new( 
        name => 'file1',
        min_level => 'warning',
        mode => 'append',
        filename => File::Spec->catfile(LOG_DIR, LOG_FILE),
    )
);
$log->warning("Starting Processing:  ".time());

#
#enter main loop
#

while($keep_going) {
    #TODO add all the shit
    
    
    

}

$log->warning("Stopped Processing:  ".time());

#
#start daemon
#
#fork and detach from parent proc
#
sub startDaemon {
    eval { Proc::Daemon::Init; };
    if($@) {
        dienice("Unable to start daemon:   $@");
    }

    #
    # Get a PID file
    #
    dienice("Already running!") if hold_pid_file($PIDFILE);
}

sub dienice($) {
    my ($package, $filename, $line) = caller;
    $log->critical("$_[0] at line $line in $filename");
    die $_[0];
}
